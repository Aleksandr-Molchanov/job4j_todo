package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;

@Repository
public class ItemDBStore {

    private final SessionFactory sf;

    public ItemDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Item add(Item item) {
        return this.tx(
                session -> {
                    session.save(item);
                    return item;
                }
        );
    }

    public boolean update(Item item) {
        return this.tx(
                session -> {
                    String hql = "update Item i "
                            + " SET i.name = :name, "
                            + " i.description = :description, "
                            + " i.created = :created "
                            + " where i.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", item.getId());
                    query.setParameter("name", item.getName());
                    query.setParameter("description", item.getDescription());
                    query.setParameter("created", item.getCreated());
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }
        );
    }

    public boolean delete(int id) {
        return this.tx(
                session -> {
                    String hql = "delete Item i "
                            + " where i.id = :id";
                    Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }
        );
    }

    public List<Item> findAll() {
        return this.tx(
                session -> session.createQuery("from ru.job4j.todo.model.Item").list()
        );
    }

    public List<Item> findByDone(boolean isDone) {
        return this.tx(
                session -> session.createQuery("from Item where done = :isDone")
                        .setParameter("isDone", isDone).list()
        );
    }

    public Item findById(int id) {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }

    public boolean setDone(int id) {
        return this.tx(
                session -> {
                    String hql = "update Item i "
                            + " SET i.done = :done "
                            + " where i.id = :id";
                    Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    query.setParameter("done", true);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }
        );
    }
}
