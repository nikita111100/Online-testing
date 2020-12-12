package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.service.QuestionService;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RestController
public class QuestionController {
    private final QuestionService questionService;
    private final TestService testService;
    private final EntityManager em;

    public QuestionController(QuestionService questionService, TestService testService, EntityManager em) {
        this.questionService = questionService;
        this.testService = testService;
        this.em = em;
    }

    @RequestMapping("/find")
    public void getQuestion (Question question){
        Question questions = questionService.findById(3l);
        // находит вопрос, печатает id,text. при попытке вытащить test
        // выдает StackOverflowError
        // возможно нужен шаблонизатор
        System.out.println(questions.getId());
        System.out.println(questions.getText());

    }

    @RequestMapping("/save")
    public void saveQuestion (Question question){
        Test test = testService.findById(1l);
        Question questions = new Question(7l,"Наконец то получилось",test);
        questionService.saveQuestion(questions);
    }

    @RequestMapping("/delete")
    public void deleteQuestion (Question question){
        Question questions = questionService.findById(3l);
        questionService.deleteQuestion(questions);
    }
    @RequestMapping("/list")
    public void testNamedQuery(){
        Query query = (Query) em.createNamedQuery("getAllQuestionsByTest");
        org.nikita111100.onlinetesting.model.persistent.Test test = testService.findById(2l);
        query.setParameter(1,test );
        List<Question> a = query.getResultList();
        System.out.println("тут идет вывод");
        for (int i = 0; i < a.size(); i++) {
            System.out.println("1");
        }

    }
}
