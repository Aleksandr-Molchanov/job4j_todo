package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDBStore implements DBStore {

    private final SessionFactory sf;

    public CategoryDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Category> add(Category category) {
        try {
            tx(session -> session.save(category), sf);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(category);
    }

    public boolean update(Category category) {
        return tx(
                session -> {
                    String hql = "update Category c "
                            + " SET c.name = :name "
                            + " where c.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", category.getId());
                    query.setParameter("name", category.getName());
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    String hql = "delete Category c "
                            + " where c.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }

    public List<Category> findAll() {
        return tx(
                session -> session.createQuery("from ru.job4j.todo.model.Category").list(), sf
        );
    }
}