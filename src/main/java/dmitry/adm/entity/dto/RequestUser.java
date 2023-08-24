package dmitry.adm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestUser {

    private int telegramId;
    private String fullName;
}