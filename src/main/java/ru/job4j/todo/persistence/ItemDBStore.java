package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemDBStore implements DBStore {

    private final SessionFactory sf;

    public ItemDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item, List<String> idCategory) {
        return tx(
                session -> {
                    for (String id : idCategory) {
                        Category category = session.find(Category.class, Integer.parseInt(id));
                        item.addCategory(category);
                    }
                    session.save(item);
                    return item;
                }, sf
        );
    }

    public boolean update(Item item, List<String> idCategory) {
        return tx(
                session -> {
                    for (String id : idCategory) {
                        Category category = session.find(Category.class, Integer.parseInt(id));
                        item.addCategory(category);
                    }
                    String hql = "update Item i "
                            + " SET i.name = :name, "
                            + " i.description = :description, "
                            + " i.created = :created, "
                            + " i.categories = :categories "
                            + " where i.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", item.getId());
                    query.setParameter("name", item.getName());
                    query.setParameter("description", item.getDescription());
                    query.setParameter("created", item.getCreated());
                    query.setParameter("categories", item.getCategories());
                    System.out.println(item.getCategories());
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    String hql = "delete Item i "
                            + " where i.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }

    public List<Item> findAll() {
        return tx(
                session -> session.createQuery("select distinct i from Item i join fetch i.categories").list(), sf
        );
    }

    public List<Item> findByDone(boolean isDone) {
        return tx(
                session -> session.createQuery("from Item where done = :isDone")
                        .setParameter("isDone", isDone).list(), sf
        );
    }

    public Item findById(int id) {
        return tx(
                session -> session.get(Item.class, id), sf
        );
    }

    public boolean setDone(int id) {
        return tx(
                session -> {
                    String hql = "update Item i "
                            + " SET i.done = :done "
                            + " where i.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    query.setParameter("done", true);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }
}
