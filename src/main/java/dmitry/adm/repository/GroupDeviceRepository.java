package dmitry.adm.repository;

import dmitry.adm.entity.model.GroupDevice;

import java.util.List;

public interface GroupDeviceRepository {

    void save(GroupDevice groupDevice);

    boolean deviceIsLinked(int groupId, int deviceId);

    void removeById(GroupDevice.PrimaryKey deviceId);

    List<GroupDevice> findAllByGroupId(int deviceId);
}
