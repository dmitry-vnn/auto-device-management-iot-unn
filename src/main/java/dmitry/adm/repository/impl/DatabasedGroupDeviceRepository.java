package dmitry.adm.repository.impl;

import dmitry.adm.entity.dto.ResponseUserForGet;
import dmitry.adm.entity.model.GroupDevice;
import dmitry.adm.repository.GroupDeviceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DatabasedGroupDeviceRepository implements GroupDeviceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(GroupDevice groupDevice) {
        entityManager.persist(groupDevice);
    }

    @Override
    public boolean deviceIsLinked(int groupId, int deviceId) {
        long count = entityManager.createQuery("""
            SELECT COUNT(*)
                FROM GroupDevice gd
                WHERE
                    gd.groupId = :groupId
                    AND gd.deviceId = :deviceId
        """, Long.class)
                .setParameter("groupId", groupId)
                .setParameter("deviceId", deviceId)
                .getSingleResult();

        return count != 0;
    }

    @Transactional
    @Override
    public void removeById(GroupDevice.PrimaryKey id) {
        entityManager
                .createQuery("DELETE FROM GroupDevice gd WHERE gd.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<GroupDevice> findAllByGroupId(int groupId) {
        return entityManager
                .createQuery(
                        "SELECT gd FROM GroupDevice gd WHERE gd.groupId = :groupId",
                        GroupDevice.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }
}
