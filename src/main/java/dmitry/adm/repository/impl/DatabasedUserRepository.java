package dmitry.adm.repository.impl;

import dmitry.adm.entity.model.User;
import dmitry.adm.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DatabasedUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isExists(int telegramId) {
        return entityManager
                .createQuery("SELECT COUNT(*) FROM User u WHERE u.telegramId = :telegramId", Long.class)
                .setParameter("telegramId", telegramId)
                .getSingleResult() != 0;
    }

    @Transactional
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<User> findByTelegramId(int telegramId) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.telegramId = :telegramId", User.class)
                .setParameter("telegramId", telegramId)
                .getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Transactional
    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}
