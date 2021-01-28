package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.services.TestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Main {

    private final TestService testService;

    public Main(TestService testServices) {
        this.testService = testServices;
    }

    @GetMapping("/")
    public String allTest(@AuthenticationPrincipal User user, Model model) {
        List<Test> tests = testService.findAll();
        model.addAttribute("tests", tests);
        return "main";
    }

}