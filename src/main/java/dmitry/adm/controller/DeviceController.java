package dmitry.adm.controller;

import dmitry.adm.entity.dto.RequestDevice;
import dmitry.adm.entity.dto.ResponseDevice;
import dmitry.adm.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices/")

public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("all")
    public List<ResponseDevice> all() {
        return deviceService.findAll();
    }

    @PostMapping("authorize")
    public void authorize(RequestDevice requestDevice) {
        deviceService.authorizeDevice(requestDevice);
    }

    @PostMapping("addValue")
    public void addValue(RequestDevice requestDevice, int value) {
        deviceService.addDeviceValue(requestDevice, value);
    }

}
