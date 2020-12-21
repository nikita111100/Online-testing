package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.service.QuestionService;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String findAll ( Model model){
        List<Question> questions = questionService.findAll();
        List<Test> tests = testService.findAll();
        model.addAttribute("tests",tests);
        model.addAttribute("questions", questions);
        return "question-list";
    }

    @GetMapping("/create")
    public String createQuestionForm(Question question) {
        return "question-create";
    }

    @PostMapping("/create")
    public String createQuestion(Question question) {
        questionService.saveQuestion(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteById(id);
        return "redirect:/questions";
    }

    @GetMapping("/{id}/update")
    public String updateQuestionForm(@PathVariable("id") Long id,Model model) {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "/question-update";
    }

    @PostMapping("/{id}/update")
    public String updateQuestion(Question question){
        questionService.saveQuestion(question);
        return "redirect:/questions";
    }
}
