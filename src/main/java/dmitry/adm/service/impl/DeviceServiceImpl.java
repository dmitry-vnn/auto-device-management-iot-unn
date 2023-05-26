package dmitry.adm.service.impl;

import dmitry.adm.entity.dto.RequestDevice;
import dmitry.adm.entity.dto.ResponseDevice;
import dmitry.adm.entity.model.Device;
import dmitry.adm.entity.model.DeviceValue;
import dmitry.adm.exception.ApiErrorException;
import dmitry.adm.exception.DeviceError;
import dmitry.adm.repository.DeviceRepository;
import dmitry.adm.repository.DeviceValueRepository;
import dmitry.adm.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceValueRepository deviceValueRepository;

    @Override
    public List<ResponseDevice> findAll() {
        return deviceRepository.findAll().stream().map(ResponseDevice::new).collect(Collectors.toList());
    }

    @Override
    public void authorizeDevice(RequestDevice requestDevice) throws ApiErrorException {
        Optional<Device> desiredDevice = deviceRepository.findById(requestDevice.getId());

        if (desiredDevice.isEmpty()) {
            deviceRepository.save(new Device(requestDevice.getId(), requestDevice.getPassword()));
            return;
        }

        comparePasswordsOrError(requestDevice, desiredDevice.get());
    }

    @Override
    public void addDeviceValue(RequestDevice requestDevice, int value) throws ApiErrorException {
        var desiredDevice = deviceRepository.findById(requestDevice.getId());

        if (desiredDevice.isEmpty()) {
            throw new ApiErrorException(DeviceError.NOT_FOUND);
        }

        comparePasswordsOrError(requestDevice, desiredDevice.get());

        deviceValueRepository.save(new DeviceValue(requestDevice.getId(), value));
    }

    private static void comparePasswordsOrError(RequestDevice requestDevice, Device desiredDevice) {
        if (!desiredDevice.getPassword().equals(requestDevice.getPassword())) {
            throw new ApiErrorException(DeviceError.WRONG_PASSWORD);
        }
    }
}
