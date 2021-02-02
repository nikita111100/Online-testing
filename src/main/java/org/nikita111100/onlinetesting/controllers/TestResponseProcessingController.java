package org.nikita111100.onlinetesting.controllers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.nikita111100.onlinetesting.model.persistent.AnswersToTest;
import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.nikita111100.onlinetesting.model.persistent.Test;
import org.nikita111100.onlinetesting.model.persistent.User;
import org.nikita111100.onlinetesting.services.AnswerQuestionService;
import org.nikita111100.onlinetesting.services.AnswerTestService;
import org.nikita111100.onlinetesting.services.PossibleAnswerService;
import org.nikita111100.onlinetesting.services.QuestionService;
import org.nikita111100.onlinetesting.services.TestService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/test")
public class TestResponseProcessingController {

    private final TestService testService;
    private final QuestionService questionService;
    private final PossibleAnswerService possibleAnswerService;
    private final AnswerTestService answerTestService;
    private final AnswerQuestionService answerQuestionService;

    public TestResponseProcessingController(TestService testService, QuestionService questionService,
                                            PossibleAnswerService possibleAnswerService,
                                            AnswerTestService answerTestService,
                                            AnswerQuestionService answerQuestionService) {
        this.testService = testService;
        this.questionService = questionService;
        this.possibleAnswerService = possibleAnswerService;
        this.answerTestService = answerTestService;
        this.answerQuestionService = answerQuestionService;
    }


    @GetMapping("/{id}/startTest")
    public String answerTest(@PathVariable("id") Long testId, Model model) {
        Optional<Test> test = testService.findById(testId);
        if (test.isPresent()) {
            model.addAttribute("test", test.get());
            AnswersToTest answersToTest = new AnswersToTest();
            model.addAttribute("answersToTest", answersToTest);
            return "testResponse/start";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/{id}/startTest")
    public String answerForm(@ModelAttribute("test") Test test,
                             @ModelAttribute(value = "answersToTest") AnswersToTest answersToTest,
                             Model model) {

        Set<String> checkedItems = answersToTest.getCheckedItems();

        AnswerTest answerTest = new AnswerTest();
        answerTest.setTest(test);
        answerTestService.save(answerTest);

        // новая коллекция с вопросами и вариантами ответа
        Multimap<Long, Long> newCheckedItems = parseMapToMultimap(checkedItems);

        // проверка на все ли вопросы ответил пользователь
        List<Question> questions = questionService.findAllByTestId(test.getId());
        for (Question question : questions) {
            Long key = question.getId();
            if (!newCheckedItems.containsKey(key)) {
                Optional<Test> testModel = testService.findById(test.getId());
                if (testModel.isPresent()) {
                    model.addAttribute("test", testModel.get());
                    model.addAttribute("message", "Ответье на все вопросы данного теста");
                    return "testResponse/start";
                } else {
                    model.addAttribute("warringMessage", "При попытке пройти тест что-то пошло не так, тест не найден");
                    return "redirect:/";
                }
            }
        }

        // подсчет количества неправильных ответов
        Double wrongAnswer = numberOfIncorrectAnswersToAQuestion(newCheckedItems, answerTest);

        double result = testResult(Double.valueOf(questions.size()), wrongAnswer);
        answerTest.setResult((int) result);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        answerTest.setUser(user);
        answerTestService.save(answerTest);
        model.addAttribute("answerTest", answerTest);
        return "testResponse/resultPage";
    }


    public Double numberOfIncorrectAnswersToAQuestion(Multimap<Long, Long> newCheckedItems, AnswerTest answerTest) {
        Double wrongAnswer = 0.0;
        Set keySet = newCheckedItems.keySet();
        Iterator keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            Long key = (Long) keyIterator.next();
            List<Long> values = (List<Long>) newCheckedItems.get(key);
            List<PossibleAnswer> countOfPossibleAnswer = possibleAnswerService.findAllByQuestionId(key);
            List<PossibleAnswer> countOfCorrectPossibleAnswers = new ArrayList<>();
            for (PossibleAnswer possibleAnswer : countOfPossibleAnswer) {
                if (possibleAnswer.getCorrectAnswer() == 1) {
                    countOfCorrectPossibleAnswers.add(possibleAnswer);
                }
            }

            List<PossibleAnswer> allUserAnswersToOneQuestion = new ArrayList<>();

            for (Long value : values) {
                AnswerQuestion answerQuestion = new AnswerQuestion();
                Optional<PossibleAnswer> possibleAnswer = possibleAnswerService.findById(value);
                allUserAnswersToOneQuestion.add(possibleAnswer.get());
                answerQuestion.setPossibleAnswer(possibleAnswer.get());
                answerQuestion.setAnswerTest(answerTest);
                answerQuestionService.save(answerQuestion);
            }
            if (countOfCorrectPossibleAnswers.size() == allUserAnswersToOneQuestion.size()) {
                for (int i = 0; i < countOfCorrectPossibleAnswers.size(); i++) {
                    if (countOfCorrectPossibleAnswers.get(i).getId() != (allUserAnswersToOneQuestion.get(i).getId())) {
                        wrongAnswer++;
                        break;
                    }
                }
            } else {
                wrongAnswer++;
            }

        }
        return wrongAnswer;
    }

    // парсит set в Multimap
    public Multimap parseMapToMultimap(Set<String> map) {
        Multimap<Long, Long> newCheckedItems = ArrayListMultimap.create();
        for (String entry : map) {
            String key = entry.split("(?=\\D)")[0];
            String value = null;
            Pattern pattern = Pattern.compile("\\d+$");
            Matcher matcher = pattern.matcher(entry);
            if (matcher.find()) {
                value = matcher.group();
            }
            newCheckedItems.put(Long.valueOf(key), Long.valueOf(value));
        }
        return newCheckedItems;
    }

    //   считает результат в процентах с округлением
    public Double testResult(Double numberOfQuestions, Double numberOfWrongAnswers) {
        Double result;
        Double numberOfTrueAnswer = (numberOfQuestions - numberOfWrongAnswers);
        if (numberOfWrongAnswers != 0) {
            result = (numberOfTrueAnswer / numberOfQuestions * 100);
            double newDouble = new BigDecimal(result).setScale(0, RoundingMode.HALF_EVEN).doubleValue();
            return newDouble;
        }
        return result = 100.0;
    }
}
