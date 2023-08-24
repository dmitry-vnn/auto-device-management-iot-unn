package dmitry.adm.service;


import dmitry.adm.entity.dto.RequestUser;
import dmitry.adm.entity.dto.ResponseUser;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(RequestUser user);

    boolean userExists(int telegramId);

    Optional<ResponseUser> findByTelegramId(int telegramId);
}
