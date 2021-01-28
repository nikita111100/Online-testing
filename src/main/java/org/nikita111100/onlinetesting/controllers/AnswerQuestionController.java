package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.nikita111100.onlinetesting.services.AnswerQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/answerQuestions")
public class AnswerQuestionController {

    private final AnswerQuestionService answerQuestionService;

    public AnswerQuestionController(AnswerQuestionService answerQuestionService) {
        this.answerQuestionService = answerQuestionService;
    }

    @GetMapping
    public String findAll(Model model) {
        List<AnswerQuestion> answerQuestions = answerQuestionService.findAll();
        model.addAttribute("answerQuestions", answerQuestions);
        return "answerQuestions/list";
    }

    @GetMapping("/{id}/delete")
    public String deleteAnswerQuestion(@PathVariable("id") Long id) {
        answerQuestionService.deleteById(id);
        return "redirect:/answerQuestions";
    }

}
