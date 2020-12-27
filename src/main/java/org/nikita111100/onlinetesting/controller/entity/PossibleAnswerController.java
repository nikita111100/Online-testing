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
@RequestMapping("/{testId}/{questionId}/possibleAnswers")
public class PossibleAnswerController {
    private final PossibleAnswerService possibleAnswerService;
    private final QuestionService questionService;

    public PossibleAnswerController(PossibleAnswerService possibleAnswerService, QuestionService questionService, TestService testService) {
        this.possibleAnswerService = possibleAnswerService;
        this.questionService = questionService;
    }

    @GetMapping
    public String findAllPossibleAnswersByQuestion(@PathVariable("questionId") Long questionId, Model model) {
        List<PossibleAnswer> possibleAnswers = possibleAnswerService.findAllPossibleAnswersByQuestionId(questionId);
        model.addAttribute("possibleAnswers", possibleAnswers);
        return "possibleAnswers/list";
    }

    @GetMapping("/create")
    public String createPossibleAnswerForm(PossibleAnswer possibleAnswer) {
        return "possibleAnswers/create";
    }

    @PostMapping("/create")
    public String createPossibleAnswer(@PathVariable("questionId") Long questionId, @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/create";
        }
        Question question = questionService.findById(questionId);
        possibleAnswer.setQuestions(question);
        possibleAnswerService.save(possibleAnswer);
        return "redirect:/{testId}/{questionId}/possibleAnswers";
    }

    @GetMapping("/{possibleAnswerId}/delete")
    public String deletePossibleAnswer(@PathVariable("possibleAnswerId") Long id) {
        if (possibleAnswerService.isExists(id)) {
            possibleAnswerService.deleteById(id);
        }
        return "redirect:/{testId}/{questionId}/possibleAnswers";
    }

    @GetMapping("/{possibleAnswerId}/update")
    public String updatePossibleAnswerForm(@PathVariable("possibleAnswerId") Long id, Model model) {
        if (possibleAnswerService.isExists(id)) {
            PossibleAnswer possibleAnswer = possibleAnswerService.findById(id);
            model.addAttribute("possibleAnswer", possibleAnswer);
            return "possibleAnswers/update";
        }
        return "redirect:/{testId}/{questionId}/possibleAnswers";
    }

    @PostMapping("/{possibleAnswerId}/update")
    public String updatePossibleAnswers(@PathVariable("questionId") Long questionId, @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/update";
        }
        Question question = questionService.findById(questionId);
        possibleAnswer.setQuestions(question);
        possibleAnswerService.save(possibleAnswer);
        return "redirect:/{testId}/{questionId}/possibleAnswers";

    }
}