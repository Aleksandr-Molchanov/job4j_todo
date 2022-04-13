package ru.job4j.todo.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute Item item) {
        itemService.add(item);
        return "redirect:/items";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/items";
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@ModelAttribute Item item) {
        itemService.delete(item.getId());
        return "redirect:/items";
    }

    @GetMapping("/newItems")
    public String newItems(Model model) {
        model.addAttribute("items", itemService.findByDone(true));
        return "items";
    }

    @GetMapping("/doneItems")
    public String doneItems(Model model) {
        model.addAttribute("items", itemService.findByDone(false));
        return "items";
    }
}
