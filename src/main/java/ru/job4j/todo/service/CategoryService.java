package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryDBStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryDBStore store;

    public CategoryService(CategoryDBStore store) {
        this.store = store;
    }

    public Optional<Category> add(Category category) {
        return store.add(category);
    }

    public void update(Category category) {
        store.update(category);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(store.findAll());
    }
}