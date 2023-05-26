package dmitry.adm.repository;

import dmitry.adm.entity.model.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository {

    void save(Device device);

    Optional<Device> findById(int id);

    List<Device> findAll();
}
