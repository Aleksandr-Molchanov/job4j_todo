package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemDBStore {

    private final SessionFactory sf;

    public ItemDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public boolean update(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        String hql = "update Item i "
                + " SET i.name = :name, "
                + " i.description = :description, "
                + " i.created = :created "
                + " where i.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", item.getId());
        query.setParameter("name", item.getName());
        query.setParameter("description", item.getDescription());
        query.setParameter("created", item.getCreated());
        int rsl = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return rsl != 0;
    }

    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        String hql = "delete Item i "
                + " where i.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        int rsl = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return rsl != 0;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List rsl = session.createQuery("from ru.job4j.todo.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public List<Item> findByDone(boolean isDone) {
        Session session = sf.openSession();
        session.beginTransaction();
        List rsl = session.createQuery("from Item where done = :isDone").list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public boolean setDone(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        String hql = "update Item i "
                + " SET i.done = :done "
                + " where i.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("done", true);
        int rsl = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return rsl != 0;
    }
}
