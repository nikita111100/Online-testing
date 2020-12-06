package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.service.UserServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserServise userServise;

    @Autowired
    public UserController(UserServise userServise) {
        this.userServise = userServise;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        List<User> users = userServise.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user) {
        userServise.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("user-delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
    userServise.deleteById(id);
    return "redirect:/users";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Integer id,Model model) {
        User user = userServise.findByid(id);
        model.addAttribute("user", user);
        return "/user-update";
    }
    @PostMapping("/user-update")
    public String updateUser(User user){
        userServise.saveUser(user);
        return "redirect:/users";
    }
}
