package dmitry.adm.repository;

import dmitry.adm.entity.model.DeviceValue;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceValueRepository {

    void save(DeviceValue deviceValue);
    
    List<DeviceValue> findValuesByIdAndPeriods(int id, LocalDateTime start, LocalDateTime end);
    

}
