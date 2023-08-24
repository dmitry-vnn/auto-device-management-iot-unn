package dmitry.adm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestCreateGroupData {

    private int telegramId;
    private String groupName;
    private String groupPassword;
}
