package dmitry.adm.repository.impl;

import dmitry.adm.entity.model.Group;
import dmitry.adm.repository.GroupRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatabasedGroupRepository implements GroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Group> findById(int id) {
        return Optional.ofNullable(entityManager.find(Group.class, id));
    }

    @Override
    public Optional<Group> findByName(String name) {
        return entityManager
                        .createQuery(
                            "SELECT g FROM Group g WHERE g.name = :name",
                            Group.class
                        )
                        .setParameter("name", name)
                        .getResultList().stream().findFirst();
    }


    @Transactional
    @Override
    public void save(Group group) {
        entityManager.persist(group);
    }

    @Transactional
    @Override
    public void delete(Group group) {
        if (!entityManager.contains(group)) {
            group = entityManager.merge(group);
        }
        entityManager.remove(group);
    }

    @Override
    public boolean groupExists(String groupName) {
        return entityManager
                .createQuery("SELECT COUNT(*) FROM Group g WHERE g.name = :groupName", Long.class)
                .setParameter("groupName", groupName)
                .getSingleResult() != 0;
    }

    @Override
    public List<String> findAllUserGroupNames(int userId) {
        return entityManager
                .createQuery("""
                            SELECT g.name
                                FROM GroupMember gm
                                JOIN Group g
                                    ON gm.groupId = g.id
                                WHERE
                                    gm.memberId = :userId
                            
                            UNION
                            
                            SELECT g.name
                                FROM Group g
                                WHERE
                                    g.ownerId = :userId
                        """, String.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    @Override
    public void update(Group group) {
        entityManager.merge(group);
    }
}
