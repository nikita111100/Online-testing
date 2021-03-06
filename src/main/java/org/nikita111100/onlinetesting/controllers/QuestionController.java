package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.services.QuestionService;
import org.nikita111100.onlinetesting.services.TestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/{testId}/questions")
@PreAuthorize("hasAuthority('ADMIN')")
public class QuestionController {
    private final QuestionService questionService;
    private final TestService testService;

    public QuestionController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
    }

    @GetMapping
    public String findAllByTest(@PathVariable("testId") Long testId, Model model) {
        Optional<Test> test = testService.findById(testId);
        if (test.isPresent()) {
            List<Question> questions = questionService.findAllByTestId(test.get().getId());
            model.addAttribute("questions", questions);
            return "questions/list";
        } else {
            return "redirect:/tests";
        }
    }

    @GetMapping("/create")
    public String createQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        return "questions/create";
    }

    @PostMapping("/create")
    public String createQuestion(@PathVariable("testId") Long testId,
                                 @Valid Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "questions/create";
        }
        questionService.create(testId, question);
        return "redirect:/{testId}/questions";
    }

    @GetMapping("/{questionId}/delete")
    public String deleteQuestion(@PathVariable("questionId") Long id) {
        questionService.deleteById(id);
        return "redirect:/{testId}/questions";
    }

    @GetMapping("/{questionId}/update")
    public String updateQuestion(@PathVariable("questionId") Long questionId, Model model) {
        Optional<Question> question = questionService.findById(questionId);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return "questions/update";
        } else {
            return "redirect:/{testId}/questions";
        }
    }

    @PostMapping("/{questionId}/update")
    public String updateQuestion(@Valid Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "questions/update";
        }
        questionService.update(question);
        return "redirect:/{testId}/questions";
    }
}
