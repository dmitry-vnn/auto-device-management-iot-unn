package dmitry.adm.repository;

import dmitry.adm.entity.dto.ResponseUserForGet;
import dmitry.adm.entity.model.GroupMember;

import java.util.List;

public interface GroupMemberRepository {

    void save(GroupMember groupMember);

    void delete(GroupMember groupMember);

    boolean isGroupMember(int groupId, int userId);

    List<ResponseUserForGet> findAllByGroupId(int groupId);
}
