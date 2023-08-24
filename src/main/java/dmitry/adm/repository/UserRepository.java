package dmitry.adm.repository;

import dmitry.adm.entity.dto.ResponseUser;
import dmitry.adm.entity.model.User;

import java.util.Optional;

public interface UserRepository {

    boolean isExists(int telegramId);

    void save(User user);

    Optional<User> findByTelegramId(int telegramId);

    Optional<User> findById(int id);

    void update(User user);
}
