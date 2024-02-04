package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("allUsers", userList);
        return "admin";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "new";
    }

    @RequestMapping("/saveUser")
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
//    @PatchMapping("/{id}")
//    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
//        userService.updateUser(id, user);
//        return "redirect:/admin";
//    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}