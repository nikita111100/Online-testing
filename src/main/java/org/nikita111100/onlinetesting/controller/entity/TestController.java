package org.nikita111100.onlinetesting.controller.entity;

import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Repository
@RequestMapping("/tests")
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
    public String createTestForm(Test test) {
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
        if (testService.isExists(id)) {
            testService.deleteById(id);
        }
        return "redirect:/tests";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        if (testService.isExists(id)) {
            Test test = testService.findById(id);
            model.addAttribute("test", test);
            return "/tests/update";
        }
        return "redirect:/tests";
    }

    @PostMapping("/{id}/update")
    public String updateTest(@Valid Test test, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/tests/update";
        }
        testService.saveTest(test);
        return "redirect:/tests";
    }

}
