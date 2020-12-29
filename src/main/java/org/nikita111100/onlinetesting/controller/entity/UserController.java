package org.nikita111100.onlinetesting.controller.entity;

import org.nikita111100.onlinetesting.model.persistent.Role;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.service.UserService;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.access.prepost.PreAuthorize;
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
//@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String AllUsers(Model model) {
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
    public String createUser(@RequestParam(value = "rolesChecked", required = false) String roles, @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/create";
        }

        User userFromDb = userService.findByName(user.getName());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь с таким именем уже зарегистрирован");
            return "/users/create";
        }
        if (roles != null) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        } else {
            user.setRoles(Collections.singleton(Role.USER));
        }
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
            return "/users/update";
        }
        return "redirect:/users";

    }

    @PostMapping("/{id}/update")
    public String updateUser(@RequestParam(value = "rolesChecked", required = false) String roles,
                             @RequestParam("name") String name,
                             @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/update";
        }
        if (roles != null) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        } else {
            user.setRoles(Collections.singleton(Role.USER));
        }
        if (userService.findByName(name) == null) {
            userService.saveUser(user);
            return "redirect:/users";
        }
        User user2 = userService.findByName(name);
        if (user.getId() == user2.getId()) {
            userService.saveUser(user);
            return "redirect:/users";
        } else if (userService.findByName(name) != null) {
            model.addAttribute("message", "Это имя занято, введите другое");
            return "/users/update";
        }
        return "redirect:/users";
    }
}
