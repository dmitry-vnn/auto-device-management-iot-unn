package dmitry.adm.repository.impl;

import dmitry.adm.entity.model.DeviceValue;
import dmitry.adm.repository.DeviceValueRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabasedDeviceValueRepository implements DeviceValueRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void save(DeviceValue deviceValue) {
        entityManager.persist(deviceValue);
    }

    @Override
    public List<DeviceValue> findValuesByIdAndPeriods(int id, LocalDateTime start, LocalDateTime end) {
        var query = entityManager.createQuery(
                """
                        SELECT dv
                            FROM DeviceValue dv
                                      
                        WHERE
                            dv.id.deviceId = :deviceId
                            AND dv.id.timeReceive BETWEEN :start AND :end
                        """, DeviceValue.class);

        return query
                .setParameter("deviceId", id)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
}
