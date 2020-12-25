package org.nikita111100.onlinetesting.controller;

import lombok.ToString;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.service.QuestionService;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final TestService testService;

    public QuestionController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
    }

    @GetMapping()
    public String findAll(Model model) {
        List<Question> questions = questionService.findAll();
        List<Test> tests = testService.findAll();
        model.addAttribute("tests", tests);
        model.addAttribute("questions", questions);
        return "questions/list";
    }

    @GetMapping("/create")
    public String createQuestionForm(Question question) {
        return "questions/create";
    }

    @PostMapping("/create")
    public String createQuestion(@RequestParam("test.id") Long test, @Valid Question question, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "questions/create";

        } else if (!testService.isExists(test)) {
            model.addAttribute("message", "тест не найден");
            return "questions/create";
        } else {
            questionService.saveQuestion(question);
            return "redirect:/questions";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }

    @GetMapping("/{id}/update")
    public String updateQuestionForm(@PathVariable("id") Long id, Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "/questions/update";
    }

    @PostMapping("/{id}/update")
    public String updateQuestion(@Valid Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/questions/update";
        }
        questionService.saveQuestion(question);
        return "redirect:/questions";
    }
}
