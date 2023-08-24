package dmitry.adm.repository;

import dmitry.adm.entity.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    Optional<Group> findById(int id);

    Optional<Group> findByName(String name);

    void save(Group group);

    void delete(Group group);

    boolean groupExists(String groupName);

    List<String> findAllUserGroupNames(int userId);

    void update(Group group);
}
