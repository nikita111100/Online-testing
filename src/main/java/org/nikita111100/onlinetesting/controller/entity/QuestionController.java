package org.nikita111100.onlinetesting.controller.entity;

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
@RequestMapping("/{testId}/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final TestService testService;

    public QuestionController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
    }

    @GetMapping
    public String findAllQuestionByTest(@PathVariable("testId") Long test, Model model) {
        List<Question> questions = questionService.findAllQuestionsByTestId(test);
        model.addAttribute("questions", questions);
        return "questions/list";
    }

    @GetMapping("/create")
    public String createQuestionForm(Question question) {
        return "questions/create";
    }

    @PostMapping("/create")
    public String createQuestion(@PathVariable("testId") Long testId, @Valid Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "questions/create";
        }
        Test test = testService.findById(testId);
        question.setTest(test);
        questionService.saveQuestion(question);
        return "redirect:/{testId}/questions";

    }

    @GetMapping("/{questionId}/delete")
    public String deleteQuestion(@PathVariable("questionId") Long id) {
        if (questionService.isExists(id)) {
            questionService.deleteById(id);
        }
        return "redirect:/{testId}/questions";
    }

    @GetMapping("/{questionId}/update")
    public String updateQuestionForm(@PathVariable("questionId") Long id, Model model) {
        if (questionService.isExists(id)) {
            Question question = questionService.findById(id);
            model.addAttribute("question", question);
            return "questions/update";
        }
        return "redirect:/{testId}/questions";
    }

    @PostMapping("/{questionId}/update")
    public String updateQuestion(@PathVariable("testId") Long testId, @Valid Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "questions/update";
        }
        Test test = testService.findById(testId);
        question.setTest(test);
        questionService.saveQuestion(question);
        return "redirect:/{testId}/questions";
    }
}
