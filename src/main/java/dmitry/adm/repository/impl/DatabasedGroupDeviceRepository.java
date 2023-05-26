package dmitry.adm.repository.impl;

import dmitry.adm.entity.model.GroupDevice;
import dmitry.adm.repository.GroupDeviceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class DatabasedGroupDeviceRepository implements GroupDeviceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(GroupDevice groupDevice) {
        entityManager.persist(groupDevice);
    }

    @Override
    public boolean deviceIsLinked(int groupId, int deviceId) {
        long count = (Long) entityManager.createQuery("""
            SELECT COUNT(*)
                FROM GroupDevice gd
                WHERE
                    gd.groupId = :groupId
                    AND gd.deviceId = :deviceId
        """)
                .setParameter("groupId", groupId)
                .setParameter("deviceId", deviceId)
                .getSingleResult();

        return count != 0;
    }
}
