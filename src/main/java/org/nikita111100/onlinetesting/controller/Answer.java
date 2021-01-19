package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.nikita111100.onlinetesting.model.persistent.AnswersToTest;
import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.service.AnswerQuestionService;
import org.nikita111100.onlinetesting.service.AnswerTestService;
import org.nikita111100.onlinetesting.service.PossibleAnswerService;
import org.nikita111100.onlinetesting.service.QuestionService;
import org.nikita111100.onlinetesting.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class Answer {

    private final TestService testService;
    private final QuestionService questionService;
    private final PossibleAnswerService possibleAnswerService;
    private final AnswerTestService answerTestService;
    private final AnswerQuestionService answerQuestionService;

    public Answer(TestService testService, QuestionService questionService, PossibleAnswerService possibleAnswerService, AnswerTestService answerTestService, AnswerQuestionService answerQuestionService) {
        this.testService = testService;
        this.questionService = questionService;
        this.possibleAnswerService = possibleAnswerService;
        this.answerTestService = answerTestService;
        this.answerQuestionService = answerQuestionService;
    }


    @GetMapping("/{id}/startTest")
    public String answerTest(@PathVariable("id") Long testId, Model model) {
        if (testService.isExists(testId)) {
            Test test = testService.findById(testId);
            model.addAttribute("test", test);
            AnswersToTest answersToTest = new AnswersToTest();
            model.addAttribute("answersToTest", answersToTest);
            return "test/start";
        }
        return "redirect:/";
    }

    @PostMapping("/{id}/startTest")
    public String answerForm(@ModelAttribute("test") Test test,
                             @ModelAttribute(value = "answersToTest") AnswersToTest answersToTest,
                             Model model) {
        Map<String, String> checkedItems = answersToTest.getCheckedItems();
        Double wrongAnswer = 0.0;

        AnswerTest answerTest = new AnswerTest();
        answerTest.setTest(test);
        answerTestService.save(answerTest);

        for (Map.Entry<String, String> entry : checkedItems.entrySet()) {
            // проверяет ответы пользователя на null
            if (entry.getValue() == null) {
                if (testService.isExists(test.getId())) {
                    Test testModel = testService.findById(test.getId());
                    model.addAttribute("test", testModel);
                    model.addAttribute("message", "Ответье на все вопросы данного теста");
                    return "test/start";
                }
                return "redirect:/";
            }
            String[] possibleAnswers = entry.getValue().split(",");
            // считает количество ответов на вопрос
            for (String answer : possibleAnswers) {
                AnswerQuestion answerQuestion = new AnswerQuestion();
                PossibleAnswer possibleAnswer = possibleAnswerService.findById(Long.valueOf(answer));
                answerQuestion.setPossibleAnswer(possibleAnswer);
                answerQuestion.setAnswerTest(answerTest);
                answerQuestionService.save(answerQuestion);
            }
            // считает количество неправильных ответов
            for (String answer : possibleAnswers) {
                PossibleAnswer possibleAnswer = possibleAnswerService.findById(Long.valueOf(answer));
                if (possibleAnswer.getCorrectAnswer() != 1) {
                    wrongAnswer++;
                    break;
                }
            }
        }
        double result = testResult(Double.valueOf(checkedItems.size()), wrongAnswer);
        answerTest.setResult((int) result);
        answerTestService.save(answerTest);
        model.addAttribute("answerTest", answerTest);
        return "test/resultPage";
    }

    // считает результат в процентах с округлением
    public Double testResult(Double numberOfQuestions, Double numberOfWrongAnswers) {
        Double result;
        Double numberOfTrueAnswer = (numberOfQuestions - numberOfWrongAnswers);
        if (numberOfWrongAnswers != 0) {
            result = (numberOfTrueAnswer / numberOfQuestions * 100);
            double newDouble2 = new BigDecimal(result).setScale(0, RoundingMode.HALF_EVEN).doubleValue();
            return newDouble2;
        }
        return result = 100.0;
    }
}
