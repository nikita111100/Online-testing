package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.model.persistent.UserReviews;
import org.nikita111100.onlinetesting.services.UserReviewsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserReviewsController {

    private final UserReviewsService userReviewsService;

    public UserReviewsController(UserReviewsService userReviewsService) {
        this.userReviewsService = userReviewsService;
    }


    @GetMapping("/reviews")
    public String showForm(Model model) {
        model.addAttribute("userReviews", new UserReviews());
        return "/reviews";
    }

    @PostMapping("/reviews")
    public String saveReviews(@Valid UserReviews userReviews, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/reviews";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userReviews.setUser(user);
        userReviewsService.save(userReviews);
        return "redirect:/";

    }
}
