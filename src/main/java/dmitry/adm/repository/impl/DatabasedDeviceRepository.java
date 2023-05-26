package dmitry.adm.repository.impl;

import dmitry.adm.entity.model.Device;
import dmitry.adm.repository.DeviceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatabasedDeviceRepository implements DeviceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Device device) {
        entityManager.persist(device);
    }

    @Override
    public Optional<Device> findById(int id) {
        return Optional.ofNullable(entityManager.find(Device.class, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Device> findAll() {
        return entityManager.createQuery("SELECT d FROM Device d").getResultList();
    }
}
