package org.nikita111100.onlinetesting.controller.entity;

import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.service.PossibleAnswerService;
import org.nikita111100.onlinetesting.service.QuestionService;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return "possibleAnswers/list";
    }

    @GetMapping("/create")
    public String createPossibleAnswerForm(PossibleAnswer possibleAnswer) {
        return "possibleAnswers/create";
    }

    @PostMapping("/create")
    public String createPossibleAnswer(@RequestParam("questions.id") Long question, @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/create";
        } else if (!questionService.isExists(question)) {
            model.addAttribute("message","вопрос не найден");
            return "possibleAnswers/create";
        } else {
            possibleAnswerService.save(possibleAnswer);
            return "redirect:/possibleAnswers";
        }
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
        return "/possibleAnswers/update";
    }

    @PostMapping("/{id}/update")
    public String updatePossibleAnswers(@RequestParam("questions.id") Long question, @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/create";
        } else if (!questionService.isExists(question)) {
            model.addAttribute("message","вопрос не найден");
            return "possibleAnswers/create";
        } else {
        possibleAnswerService.save(possibleAnswer);
        return "redirect:/possibleAnswers";}
    }
}
