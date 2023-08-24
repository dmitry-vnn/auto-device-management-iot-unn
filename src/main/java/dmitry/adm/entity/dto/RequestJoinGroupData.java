package dmitry.adm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestJoinGroupData {

    private int telegramId;

    private String groupName;
    private String groupPassword;

}
