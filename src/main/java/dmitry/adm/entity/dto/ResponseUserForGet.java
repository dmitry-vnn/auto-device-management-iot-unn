package dmitry.adm.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseUserForGet {

    private int telegramId;
    private String fullName;

    @JsonProperty(value="isOwner")
    private boolean isOwner;


    public ResponseUserForGet(int telegramId, String fullName) {
        this(telegramId, fullName, false);
    }
}
