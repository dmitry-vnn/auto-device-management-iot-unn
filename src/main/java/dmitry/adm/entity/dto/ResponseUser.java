package dmitry.adm.entity.dto;

import dmitry.adm.entity.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseUser {

    private int id;
    private int telegramId;
    private String fullName;
    private Integer activeGroup;

    public ResponseUser(User user) {
        this.id = user.getId();
        this.telegramId = user.getTelegramId();
        this.fullName = user.getFullName();
        this.activeGroup = user.getActiveGroupId();
    }
}
