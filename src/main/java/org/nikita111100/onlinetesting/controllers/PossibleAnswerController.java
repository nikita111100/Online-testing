package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.services.PossibleAnswerService;
import org.nikita111100.onlinetesting.services.QuestionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/{testId}/{questionId}/possibleAnswers")
@PreAuthorize("hasAuthority('ADMIN')")
public class PossibleAnswerController {
    private final PossibleAnswerService possibleAnswerService;
    private final QuestionService questionService;

    public PossibleAnswerController(PossibleAnswerService possibleAnswerService,
                                    QuestionService questionService) {
        this.possibleAnswerService = possibleAnswerService;
        this.questionService = questionService;
    }

    @GetMapping
    public String findAllByQuestion(@PathVariable("questionId") Long questionId, Model model) {
        Optional<Question> question = questionService.findById(questionId);
        if (question.isPresent()) {
            List<PossibleAnswer> possibleAnswers = possibleAnswerService.findAllByQuestionId(question.get().getId());
            model.addAttribute("question", questionService.findById(questionId).get());
            model.addAttribute("possibleAnswers", possibleAnswers);
            return "possibleAnswers/list";
        } else {
            return "redirect:/{testId}/questions";
        }
    }

    @GetMapping("/create")
    public String createPossibleAnswer(Model model) {
        model.addAttribute("possibleAnswer", new PossibleAnswer());
        return "possibleAnswers/create";
    }

    @PostMapping("/create")
    public String createPossibleAnswer(@RequestParam(value = "correctChecked", required = false) String rolesChecked,
                                       @PathVariable("questionId") Long questionId,
                                       @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/create";
        }
        possibleAnswerService.createPossibleAnswerForm(rolesChecked, possibleAnswer, questionId);

        return "redirect:/{testId}/{questionId}/possibleAnswers";
    }

    @GetMapping("/{possibleAnswerId}/delete")
    public String deletePossibleAnswer(@PathVariable("possibleAnswerId") Long id) {
        possibleAnswerService.deleteById(id);
        return "redirect:/{testId}/{questionId}/possibleAnswers";
    }

    @GetMapping("/{possibleAnswerId}/update")
    public String updatePossibleAnswer(@PathVariable("possibleAnswerId") Long id, Model model) {
        Optional<PossibleAnswer> possibleAnswer = possibleAnswerService.findById(id);
        if (possibleAnswer.isPresent()) {
            model.addAttribute("possibleAnswer", possibleAnswer.get());
            return "possibleAnswers/update";
        } else {
            return "redirect:/{testId}/{questionId}/possibleAnswers";
        }
    }

    @PostMapping("/{possibleAnswerId}/update")
    public String updatePossibleAnswers(@RequestParam(value = "correctChecked", required = false) String rolesChecked,
                                        @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/update";
        }
        possibleAnswerService.updatePossibleAnswerForm(rolesChecked, possibleAnswer);
        return "redirect:/{testId}/{questionId}/possibleAnswers";

    }
}
