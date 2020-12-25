package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public String findAll(Model model){
        List<Test> tests = testService.findAll();
        model.addAttribute("tests",tests);
        return "tests/list";
    }

    @GetMapping("/create")
    public String createTestForm(Test test) {
        return "tests/create";
    }

    @PostMapping("/create")
    public String createTest(Test test) {
        testService.saveTest(test);
        return "redirect:/tests";
    }

    @GetMapping("/{id}/delete")
    public String deleteTest(@PathVariable("id") Long id) {
        testService.deleteById(id);
        return "redirect:/tests";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable("id") Long id,Model model) {
        Test test = testService.findById(id);
        model.addAttribute("test", test);
        return "/tests/update";
    }

    @PostMapping("/{id}/update")
    public String updateTest(Test test){
        testService.saveTest(test);
        return "redirect:/tests";
    }

}
