package dmitry.adm.entity.dto;

import dmitry.adm.entity.model.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDevice {

    private int id;

    public ResponseDevice(Device device) {
        this.id = device.getId();
    }
}
