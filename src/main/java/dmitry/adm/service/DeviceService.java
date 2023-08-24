package dmitry.adm.service;


import dmitry.adm.entity.dto.DeviceStats;
import dmitry.adm.entity.dto.RequestDevice;
import dmitry.adm.entity.dto.ResponseDevice;
import dmitry.adm.error.ApiErrorException;

import java.util.List;

public interface DeviceService {

    List<ResponseDevice> findAll();

    void authorizeDevice(RequestDevice requestDevice) throws ApiErrorException;

    void addDeviceValue(RequestDevice requestDevice, int value) throws ApiErrorException;


    List<DeviceStats> getStats(int deviceId, int lastHours);
}
