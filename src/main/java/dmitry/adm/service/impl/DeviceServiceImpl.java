package dmitry.adm.service.impl;

import dmitry.adm.entity.dto.DeviceStats;
import dmitry.adm.entity.dto.RequestDevice;
import dmitry.adm.entity.dto.ResponseDevice;
import dmitry.adm.entity.model.Device;
import dmitry.adm.entity.model.DeviceValue;
import dmitry.adm.error.ApiErrorException;
import dmitry.adm.error.ErrorType;
import dmitry.adm.repository.DeviceRepository;
import dmitry.adm.repository.DeviceValueRepository;
import dmitry.adm.service.DeviceService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
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
            throw new ApiErrorException(ErrorType.DEVICE_NOT_FOUND);
        }

        comparePasswordsOrError(requestDevice, desiredDevice.get());

        deviceValueRepository.save(new DeviceValue(requestDevice.getId(), value));
    }

    @Override
    public List<DeviceStats> getStats(int deviceId, int lastHours) {
        var desiredDevice = deviceRepository.findById(deviceId);

        if (desiredDevice.isEmpty()) {
            throw new ApiErrorException(ErrorType.DEVICE_NOT_FOUND);
        }

        LocalDateTime endTime = LocalDateTime.now();

        LocalDateTime startTime = endTime.minusHours(lastHours);

        List<DeviceValue> values = deviceValueRepository.findValuesByIdAndPeriods(deviceId, startTime, endTime);

        return groupValuesByHours(values, lastHours, startTime);
    }

    private List<DeviceStats> groupValuesByHours(List<DeviceValue> values, int totalHours, LocalDateTime startTime) {


        Map<LocalDateTime, ValueAndCount> groupedValues = new TreeMap<>();

        LocalDateTime localDateTime = LocalDateTime.of(
                startTime.toLocalDate(),
                LocalTime.of(
                        startTime.getHour(),
                        0
                )
        );

        for (int i = 0; i < totalHours; i++) {
            localDateTime = localDateTime.plusHours(1);

            groupedValues.put(localDateTime, null);
        }


        for (DeviceValue deviceValue : values) {

            LocalDateTime timeReceive = deviceValue.getTimeReceive();

            LocalDateTime ofLastHourTime = LocalDateTime.of(
                    timeReceive.toLocalDate(),
                    LocalTime.of(
                            timeReceive.getHour(),
                            0
                    )
            );

            ofLastHourTime = ofLastHourTime.plusHours(1);

            if (!groupedValues.containsKey(ofLastHourTime)) {
                continue;
            }

            var valueAndCount = groupedValues.get(ofLastHourTime);
            if (valueAndCount == null) {
                groupedValues.put(ofLastHourTime, new ValueAndCount(deviceValue.getValue(), 1));
            } else {
                valueAndCount.value += deviceValue.getValue();
                valueAndCount.count++;
            }

        }


        return groupedValues.entrySet().stream()
                .map(record -> {
                    var valueAndCount = record.getValue();

                    return new DeviceStats(
                            record.getKey(), valueAndCount == null ? null : valueAndCount.average()
                    );
                })
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    private static class ValueAndCount {

        int value;
        int count;

        int average() {
            return value / count;
        }

    }

    private static LocalDateTime localDateTimeFrom(int lastHour, LocalDateTime lastLocalDateTime) {
        return LocalDateTime.of(
                lastLocalDateTime.toLocalDate(), LocalTime.of(lastHour, 0)
        );
    }

    private static void comparePasswordsOrError(RequestDevice requestDevice, Device desiredDevice) {
        if (!desiredDevice.getPassword().equals(requestDevice.getPassword())) {
            throw new ApiErrorException(ErrorType.DEVICE_WRONG_PASSWORD);
        }
    }
}
