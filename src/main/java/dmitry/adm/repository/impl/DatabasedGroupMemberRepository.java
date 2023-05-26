package dmitry.adm.repository.impl;

import dmitry.adm.entity.model.GroupMember;
import dmitry.adm.repository.GroupMemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DatabasedGroupMemberRepository implements GroupMemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GroupMember> findByGroupIdAndMemberId(int groupId, int memberId) {
        return Optional.ofNullable(entityManager.find(GroupMember.class, new GroupMember.PrimaryKey(groupId, memberId)));
    }

    @Override
    public void save(GroupMember groupDevice) {
        entityManager.persist(groupDevice);
    }
}
