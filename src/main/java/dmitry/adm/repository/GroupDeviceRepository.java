package dmitry.adm.repository;

import dmitry.adm.entity.model.GroupDevice;

public interface GroupDeviceRepository {

    void save(GroupDevice groupDevice);

    boolean deviceIsLinked(int groupId, int deviceId);

}
