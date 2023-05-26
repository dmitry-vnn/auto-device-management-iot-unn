package dmitry.adm.repository;

import dmitry.adm.entity.model.GroupDevice;
import dmitry.adm.entity.model.GroupMember;

import java.util.Optional;

public interface GroupMemberRepository {

    Optional<GroupMember> findByGroupIdAndMemberId(int groupId, int memberId);

    void save(GroupMember groupMember);

    void delete(GroupMember groupMember);

}
