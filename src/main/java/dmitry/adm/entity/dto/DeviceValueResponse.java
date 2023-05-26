package dmitry.adm.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dmitry.adm.entity.model.DeviceValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DeviceValueResponse {

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime timeReceive;

    private int value;

    public DeviceValueResponse(DeviceValue deviceValue) {
        this.timeReceive = deviceValue.getTimeReceive();
        this.value = deviceValue.getValue();
    }
}
