package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.services.AnswerTestService;
import org.nikita111100.onlinetesting.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccountController {

    private final UserService userService;

    private final AnswerTestService answerTestService;

    public AccountController(UserService userService, AnswerTestService answerTestService) {
        this.userService = userService;
        this.answerTestService = answerTestService;
    }

    @GetMapping("/account")
    public String account(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AnswerTest> answers = answerTestService.findAllByUserId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("answers", answers);

        return "account/account.html";
    }
}
