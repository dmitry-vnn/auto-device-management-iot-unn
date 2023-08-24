package dmitry.adm.repository.impl;

import dmitry.adm.entity.dto.ResponseUserForGet;
import dmitry.adm.entity.model.GroupMember;
import dmitry.adm.repository.GroupMemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DatabasedGroupMemberRepository implements GroupMemberRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public void save(GroupMember groupMember) {
        entityManager.persist(groupMember);
    }

    @Transactional
    @Override
    public void delete(GroupMember groupMember) {
        if (!entityManager.contains(groupMember)) {
            groupMember = entityManager.merge(groupMember);
        }
        entityManager.remove(groupMember);
    }

    @Override
    public boolean isGroupMember(int groupId, int memberId) {
        return entityManager
                .createQuery("""
                            SELECT COUNT(*)
                                FROM GroupMember gm
                                WHERE
                                    gm.groupId = :groupId
                                    AND gm.memberId = :memberId
                        """, Long.class)
                .setParameter("groupId", groupId)
                .setParameter("memberId", memberId)
                .getSingleResult() != 0;
    }

    @Override
    public List<ResponseUserForGet> findAllByGroupId(int groupId) {
        var list = entityManager
                .createQuery("""
                        SELECT m.telegramId, m.fullName
                            FROM GroupMember gm
                            JOIN User m
                                ON gm.memberId = m.id
                            WHERE gm.groupId = :groupId
                            """, Object[].class
                )
                .setParameter("groupId", groupId)
                .getResultList();

        return list.stream().map(
                record -> new ResponseUserForGet((Integer) record[0], (String) record[1])
        ).collect(Collectors.toList());
    }
}
