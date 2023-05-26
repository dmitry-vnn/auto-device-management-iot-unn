package dmitry.adm.service;


import dmitry.adm.entity.dto.RequestDevice;
import dmitry.adm.entity.dto.ResponseDevice;
import dmitry.adm.exception.ApiErrorException;

import java.util.List;

public interface DeviceService {

    List<ResponseDevice> findAll();

    void authorizeDevice(RequestDevice requestDevice) throws ApiErrorException;

    void addDeviceValue(RequestDevice requestDevice, int value) throws ApiErrorException;
}
