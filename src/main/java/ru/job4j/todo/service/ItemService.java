package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemDBStore;

import java.util.Collection;

@Service
public class ItemService {

    private final ItemDBStore store;

    public ItemService(ItemDBStore store) {
        this.store = store;
    }

    public void add(Item item) {
        store.add(item);
    }

    public void update(Item item) {
        store.update(item);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public Collection<Item> findAll() {
        return store.findAll();
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public Collection<Item> findByDone(boolean isDone) {
        return store.findByDone(isDone);
    }

    public void setDone(int id) {
        store.setDone(id);
    }

}