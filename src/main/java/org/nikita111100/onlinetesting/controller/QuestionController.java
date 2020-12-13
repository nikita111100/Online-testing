package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.service.QuestionService;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QuestionController {
    private final TestService testService;
    private final QuestionService questionService;

    public QuestionController(TestService testService, QuestionService questionService) {
        this.testService = testService;
        this.questionService = questionService;
    }


    @RequestMapping("/find")
    public void getQuestion (){
        Question question = questionService.findById(1l);
    }

    @RequestMapping("/save")
    public void saveQuestion (){
        Test test = testService.findById(1l);
        Question question = new Question(1l,"Что такое java",test);
        questionService.saveQuestion(question);
    }

    @RequestMapping("/delete")
    public void deleteQuestion (Question question){
        questionService.deleteById(1l);
    }
    @RequestMapping("/list")
    public void testNamedQuery(){
        questionService.findAll();
    }
}
