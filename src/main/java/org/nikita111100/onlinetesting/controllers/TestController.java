package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.services.TestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Repository
@RequestMapping("/tests")
@PreAuthorize("hasAuthority('ADMIN')")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<Test> tests = testService.findAll();
        model.addAttribute("tests", tests);
        return "tests/list";
    }

    @GetMapping("/create")
    public String createTestForm(Model model) {
        model.addAttribute("test", new Test());
        return "tests/create";
    }

    @PostMapping("/create")
    public String createTest(@Valid Test test, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tests/create";
        }
        testService.saveTest(test);
        return "redirect:/tests";
    }

    @GetMapping("/{id}/delete")
    public String deleteTest(@PathVariable("id") Long id) {
        testService.deleteById(id);
        return "redirect:/tests";
    }

    @GetMapping("/{id}/update")
    public String updateTest(@PathVariable("id") Long id, Model model) {
        Optional<Test> test = testService.findById(id);
        if (test.isPresent()) {
            model.addAttribute("test", test.get());
            return "tests/update";
        } else {
            return "redirect:/tests";
        }
    }

    @PostMapping("/{id}/update")
    public String updateTest(@Valid Test test, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tests/update";
        }
        testService.saveTest(test);
        return "redirect:/tests";
    }

}
