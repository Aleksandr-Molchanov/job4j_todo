package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import javax.persistence.Query;
import java.util.Optional;

@Repository
public class UserDBStore implements DBStore {

    private final SessionFactory sf;

    public UserDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<User> add(User user) {
        try {
            tx(session -> session.save(user), sf);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    public boolean update(User user) {
        return tx(
                session -> {
                    String hql = "update User u "
                            + " SET u.name = :name, "
                            + " u.email = :email, "
                            + " u.password = :password "
                            + " where u.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", user.getId());
                    query.setParameter("name", user.getName());
                    query.setParameter("email", user.getEmail());
                    query.setParameter("password", user.getPassword());
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    String hql = "delete User u "
                            + " where u.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }

    public Optional<User> findByEmailAndPwd(String email, String password) {
        return tx(
                session -> {
                    String hql = "from User u "
                            + " where u.email = :email and u.password = :password";
                    final Query query = session.createQuery(hql);
                    query.setParameter("email", email);
                    query.setParameter("password", password);
                    return Optional.empty();
                }, sf
        );
    }
}