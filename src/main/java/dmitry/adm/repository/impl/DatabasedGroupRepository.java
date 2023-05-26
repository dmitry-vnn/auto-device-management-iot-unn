package dmitry.adm.repository.impl;

import dmitry.adm.entity.model.Group;
import dmitry.adm.repository.GroupRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatabasedGroupRepository implements GroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Group> findById(int id) {
        return Optional.ofNullable(entityManager.find(Group.class, id));
    }

    @Override
    public Optional<Group> findByName(String name) {
        return extractSingleGroupFromQuery(
                entityManager.createQuery("SELECT g FROM Group g WHERE g.name = :name").setParameter("name", name)
        );
    }

    @SuppressWarnings("unchecked")
    private static Optional<Group> extractSingleGroupFromQuery(Query query) {
        List<Group> singleList = query.getResultList();
        if (singleList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(singleList.get(0));
    }


    @Override
    public void save(Group group) {
        entityManager.persist(group);
    }

    @Override
    public void delete(Group group) {
        entityManager.remove(group);
    }
}
