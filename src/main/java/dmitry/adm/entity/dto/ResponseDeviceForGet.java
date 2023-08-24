package dmitry.adm.entity.dto;

import dmitry.adm.entity.model.GroupDevice;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDeviceForGet {

    private int deviceId;
    private String deviceDescription;

    public ResponseDeviceForGet(GroupDevice groupDevice) {
        this.deviceId = groupDevice.getDeviceId();
        this.deviceDescription = groupDevice.getDeviceDescription();
    }
}
