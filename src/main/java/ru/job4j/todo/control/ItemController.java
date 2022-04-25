package ru.job4j.todo.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.time.LocalDateTime;

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

    @PostMapping("/saveItem")
    public String saveItem(@ModelAttribute Item item) {
        item.setCreated(LocalDateTime.now().withNano(0));
        itemService.add(item);
        return "redirect:/items";
    }

    @GetMapping("/addItem")
    public String addItem(Model model) {
        return "addItem";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        item.setCreated(LocalDateTime.now().withNano(0));
        itemService.update(item);
        return "redirect:/items";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/setDone/{itemId}")
    public String setDone(@PathVariable("itemId") int id) {
        itemService.setDone(id);
        return "redirect:/items";
    }

    @GetMapping("/newItems")
    public String newItems(Model model) {
        model.addAttribute("items", itemService.findByDone(false));
        return "items";
    }

    @GetMapping("/doneItems")
    public String doneItems(Model model) {
        model.addAttribute("items", itemService.findByDone(true));
        return "items";
    }

    @GetMapping("/descriptionItem/{itemId}")
    public String descriptionItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "descriptionItem";
    }
}
