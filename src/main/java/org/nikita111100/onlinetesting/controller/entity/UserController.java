package org.nikita111100.onlinetesting.controller.entity;

import org.nikita111100.onlinetesting.model.persistent.Role;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String allUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/create")
    public String createUserForm(User user, Model model) {
        model.addAttribute("roles", Role.values());
        return "users/create";
    }

    @PostMapping("/create")
    public String createUser(@Valid User user, BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/create";
        }
        User userFromDb = userService.findByName(user.getName());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким именем уже зарегистрирован");
            return "/users/create";
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        if (userService.ifExists(id)) {
            userService.deleteById(id);
        }
        return "redirect:/users";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        if (userService.ifExists(id)) {
            User user = userService.findById(id);
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "users/update";
        }
        return "redirect:/users";

    }

    @PostMapping("/{id}/update")
    public String updateUser(@RequestParam(name = "roles[]", required = false) String[] roles,
                             @Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Role.values());
            return "users/update";
        }
        if (roles != null) {
            user.getRoles().clear();
            Arrays.stream(roles).forEach(role -> user.getRoles().add(Role.valueOf(role)));
        }
        if (userService.findByName(user.getName()) == null) {
            userService.saveUser(user);
            return "redirect:/users";
        }

        User anotherUser = userService.findByName(user.getName());

        if (user.getId().equals(anotherUser.getId())) {
            userService.saveUser(user);
            return "redirect:/users";
        } else{
            model.addAttribute("message", "Это имя занято, введите другое");
            model.addAttribute("roles", Role.values());
            return "users/update";
        }
    }
}

