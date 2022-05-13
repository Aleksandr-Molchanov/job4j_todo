package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@ThreadSafe
@Controller
public class ItemController {

    private final ItemService itemService;

    private final CategoryService categoryService;

    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/items")
    public String items(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @PostMapping("/saveItem")
    public String saveItem(@ModelAttribute Item item, HttpSession session, @RequestParam("category.id") List<String> idCategory) {
        User user = (User) session.getAttribute("user");
        item.setCreated(new Date(System.currentTimeMillis()));
        item.setUser(user);
        itemService.add(item, idCategory);
        return "redirect:/items";
    }

    @GetMapping("/addItem")
    public String addItem(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addItem";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("item", new Item());
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item, HttpSession session, @RequestParam("category.id") List<String> idCategory) {
        User user = (User) session.getAttribute("user");
        item.setCreated(new Date(System.currentTimeMillis()));
        item.setUser(user);
        System.out.println("Контроллер" + idCategory);
        itemService.update(item, idCategory);
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
    public String newItems(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findByDone(false));
        return "items";
    }

    @GetMapping("/doneItems")
    public String doneItems(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.findByDone(true));
        return "items";
    }

    @GetMapping("/descriptionItem/{itemId}")
    public String descriptionItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("item", itemService.findById(id));
        return "descriptionItem";
    }
}
