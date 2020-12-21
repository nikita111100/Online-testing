package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.service.PossibleAnswerService;
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
@RequestMapping("/possibleAnswers")
public class PossibleAnswerController {
    private final PossibleAnswerService possibleAnswerService;
    private final QuestionService questionService;

    public PossibleAnswerController(PossibleAnswerService possibleAnswerService, QuestionService questionService, TestService testService) {
        this.possibleAnswerService = possibleAnswerService;
        this.questionService = questionService;
    }

    @GetMapping
    public String findAll(Model model){
        List<PossibleAnswer> possibleAnswers = possibleAnswerService.findAll();
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        model.addAttribute("possibleAnswers",possibleAnswers);
        return "possibleAnswer-list";
    }
    @GetMapping("/create")
    public String createPossibleAnswerForm(PossibleAnswer possibleAnswer) {
        return "possibleAnswer-create";
    }

    @PostMapping("/create")
    public String createPossibleAnswer(PossibleAnswer possibleAnswer) {
        possibleAnswerService.savePossibleAnswer(possibleAnswer);
        return "redirect:/possibleAnswers";
    }
    @GetMapping("/{id}/delete")
    public String deletePossibleAnswer(@PathVariable("id") Long id) {
        possibleAnswerService.deleteById(id);
        return "redirect:/possibleAnswers";
    }

    @GetMapping("/{id}/update")
    public String updatePossibleAnswerForm(@PathVariable("id") Long id,Model model) {
        PossibleAnswer possibleAnswer = possibleAnswerService.findById(id);
        model.addAttribute("possibleAnswer", possibleAnswer);
        return "/possibleAnswer-update";
    }

    @PostMapping("/{id}/update")
    public String updatePossibleAnswers(PossibleAnswer possibleAnswer){
        possibleAnswerService.savePossibleAnswer(possibleAnswer);
        return "redirect:/possibleAnswers";
    }
}
