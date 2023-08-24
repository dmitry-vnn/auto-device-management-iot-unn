package dmitry.adm.service.impl;

import dmitry.adm.entity.dto.RequestUser;
import dmitry.adm.entity.dto.ResponseUser;
import dmitry.adm.entity.model.User;
import dmitry.adm.error.ApiErrorException;
import dmitry.adm.error.ErrorType;
import dmitry.adm.repository.UserRepository;
import dmitry.adm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(RequestUser user) {

        if (userRepository.isExists(user.getTelegramId())) {
            throw new ApiErrorException(ErrorType.USER_ALREADY_EXISTS);
        }

        userRepository.save(new User(user.getTelegramId(), user.getFullName()));

    }

    @Override
    public boolean userExists(int telegramId) {
        return userRepository.isExists(telegramId);
    }

    @Override
    public Optional<ResponseUser> findByTelegramId(int telegramId) {
        Optional<User> desiredUser = userRepository.findByTelegramId(telegramId);
        return desiredUser.map(ResponseUser::new);
    }

}
