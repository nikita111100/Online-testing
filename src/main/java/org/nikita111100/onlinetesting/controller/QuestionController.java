package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.service.QuestionServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class QuestionController {

    private final QuestionServise questionServise;

    @Autowired
    public QuestionController(QuestionServise questionServise) {
        this.questionServise = questionServise;
    }

    @GetMapping("/questions")
    public String findAll (Model model){
        List<Question> questions = questionServise.findAll();
        model.addAttribute("questions", questions);
        return "question-list";
    }
    @GetMapping("/question-create")
    public String createQuestionForm(Question question) {
        return "question-create";
    }

    @PostMapping("/question-create")
    public String createQuestion(Question question) {
       questionServise.saveQuestion(question);
        return "redirect:/questions";
    }

    @GetMapping("question-delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id) {
        questionServise.deleteById(id);
        return "redirect:/questions";
    }

    @GetMapping("/question-update/{id}")
    public String updateUserForm(@PathVariable("id") Integer id,Model model) {
        Question question = questionServise.findByid(id);
        model.addAttribute("question", question);
        return "/question-update";
    }
    @PostMapping("/question-update")
    public String updatequestion(Question question){
        questionServise.saveQuestion(question);
        return "redirect:/questions";
    }
}
