package dmitry.adm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestLinkDeviceData {

    private int telegramId;

    private int deviceId;
    private String devicePassword;

    private String deviceDescription;

}
