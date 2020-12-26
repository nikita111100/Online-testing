package org.nikita111100.onlinetesting.controller.entity;

import org.nikita111100.onlinetesting.model.persistent.Role;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.service.UserService;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.BindException;
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
        return "users/list";
    }

    @GetMapping("/create")
    public String createUserForm(User user) {
        return "users/create";
    }

    @PostMapping("/create")
    public String createUser(@Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/users/create";
        }

        User userFromDb = userService.findByName(user.getName());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким именем уже зарегистрирован");
            return "/users/create";
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
        return "/users/update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@Valid User user, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()) {
            return "/users/update";
        }
        User userFromDb = userService.findByName(user.getName());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким именем уже зарегистрирован");
            return "/users/create";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(user);
        return "redirect:/users";
    }
}
