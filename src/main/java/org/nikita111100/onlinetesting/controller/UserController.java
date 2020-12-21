package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.Role;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public  String AllUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }
    @GetMapping("/create")
    public String createUserForm(User user) {
        return "user-create";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        User userFromDb = userService.findByName(user.getName());
        if (userFromDb != null) {
            return "/user-update";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable("id") Long id,Model model) {
       User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/user-update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(User user){
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(user);
        return "redirect:/users";
    }
}
