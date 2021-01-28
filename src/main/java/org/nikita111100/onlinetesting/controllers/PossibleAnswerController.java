package org.nikita111100.onlinetesting.controllers;

import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.services.PossibleAnswerService;
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
                                    QuestionService questionService,
                                    TestService testService) {
        this.possibleAnswerService = possibleAnswerService;
        this.questionService = questionService;
    }

    @GetMapping
    public String findAllPossibleAnswersByQuestion(@PathVariable("questionId") Long questionId, Model model) {
        Optional<Question> question = questionService.findById(questionId);
        if (question.isPresent()) {
            List<PossibleAnswer> possibleAnswers = possibleAnswerService.findAllByQuestionId(questionId);
            model.addAttribute("question", questionService.findById(questionId).get());
            model.addAttribute("possibleAnswers", possibleAnswers);
            return "possibleAnswers/list";
        } else {
            return "redirect:/{testId}/questions";
        }
    }

    @GetMapping("/create")
    public String createPossibleAnswerForm(PossibleAnswer possibleAnswer) {
        return "possibleAnswers/create";
    }

    @PostMapping("/create")
    public String createPossibleAnswer(@RequestParam(value = "correctChecked", required = false) String rolesChecked,
                                       @PathVariable("questionId") Long questionId,
                                       @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/create";
        }
        if (rolesChecked != null) {
            possibleAnswer.setCorrectAnswer(1);
        } else {
            possibleAnswer.setCorrectAnswer(0);
        }
        Optional<Question> question = questionService.findById(questionId);
        if (question.isPresent()) {
            possibleAnswer.setQuestions(question.get());
            possibleAnswerService.save(possibleAnswer);
            return "redirect:/{testId}/{questionId}/possibleAnswers";
        }
        return "redirect:/{testId}/questions";
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
            Optional<PossibleAnswer> possibleAnswer = possibleAnswerService.findById(id);
            model.addAttribute("possibleAnswer", possibleAnswer);
            return "possibleAnswers/update";
        }
        return "redirect:/{testId}/{questionId}/possibleAnswers";
    }

    @PostMapping("/{possibleAnswerId}/update")
    public String updatePossibleAnswers(@RequestParam(value = "correctChecked", required = false) String rolesChecked,
                                        @Valid PossibleAnswer possibleAnswer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "possibleAnswers/update";
        }
        if (rolesChecked != null) {
            possibleAnswer.setCorrectAnswer(1);
        } else {
            possibleAnswer.setCorrectAnswer(0);
        }
        possibleAnswerService.save(possibleAnswer);
        return "redirect:/{testId}/{questionId}/possibleAnswers";

    }
}
