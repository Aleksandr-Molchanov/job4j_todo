package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistence.UserDBStore;

import java.util.Optional;

@Service
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public void update(User user) {
        store.update(user);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public Optional<User> findByEmailAndPwd(String email, String password) {
        return store.findByEmailAndPwd(email, password);
    }
}
