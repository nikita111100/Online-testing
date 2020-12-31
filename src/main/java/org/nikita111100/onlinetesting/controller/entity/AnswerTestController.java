package org.nikita111100.onlinetesting.controller.entity;

import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.nikita111100.onlinetesting.service.AnswerTestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/answerTests")
public class AnswerTestController {

    private final AnswerTestService answerTestService;

    public AnswerTestController(AnswerTestService answerTestService) {
        this.answerTestService = answerTestService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<AnswerTest> answerTests = answerTestService.findAll();
        model.addAttribute("answerTests", answerTests);
        return "answerTests/list";
    }

    @GetMapping("/{id}/delete")
    public String deleteAnswerTest(@PathVariable("id") Long id) {
        answerTestService.deleteBy(id);
        return "redirect:/answerTests";
    }
}

