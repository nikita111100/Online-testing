package org.nikita111100.onlinetesting.controller.entity;

import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.service.AnswerQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/answerQuestions")
public class AnswerQuestionController {

    private final AnswerQuestionService answerQuestionService;

    public AnswerQuestionController(AnswerQuestionService answerQuestionService) {
        this.answerQuestionService = answerQuestionService;
    }
    @GetMapping
    public String findAll(Model model){
        List<AnswerQuestion> answerQuestions = answerQuestionService.findAll();
        model.addAttribute("answerQuestions", answerQuestions);
        return "answerQuestions/list";
    }

    @GetMapping("/{id}/delete")
    public String deleteanswerQuestion(@PathVariable("id") Long id) {
        answerQuestionService.deleteById(id);
        return "redirect:/answerQuestions";
    }

}
