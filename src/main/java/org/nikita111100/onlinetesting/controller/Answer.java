package org.nikita111100.onlinetesting.controller;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.nikita111100.onlinetesting.model.persistent.AnswerQuestion;
import org.nikita111100.onlinetesting.model.persistent.AnswerTest;
import org.nikita111100.onlinetesting.model.persistent.AnswersToTest;
import org.nikita111100.onlinetesting.model.persistent.PossibleAnswer;
import org.nikita111100.onlinetesting.model.persistent.Question;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/test")
public class Answer {

    private final TestService testService;
    private final QuestionService questionService;
    private final PossibleAnswerService possibleAnswerService;
    private final AnswerTestService answerTestService;
    private final AnswerQuestionService answerQuestionService;

    public Answer(TestService testService, QuestionService questionService,
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

        Set<String> checkedItems = answersToTest.getCheckedItems();

        AnswerTest answerTest = new AnswerTest();
        answerTest.setTest(test);
        answerTestService.save(answerTest);

        // новая коллекция с вопросами и вариантами ответа
        Multimap<Long, Long> newCheckedItems = parseMapToMultimap(checkedItems);

        // проверка на все ли вопросы ответил пользователь
        List<Question> questions = questionService.findAllQuestionsByTestId(test.getId());
        for (Question question : questions) {
            Long key = question.getId();
            if (!newCheckedItems.containsKey(key)) {
                if (testService.isExists(test.getId())) {
                    Test testModel = testService.findById(test.getId());
                    model.addAttribute("test", testModel);
                    model.addAttribute("message", "Ответье на все вопросы данного теста");
                    return "test/start";
                }
                model.addAttribute("warringMessage", "При попытке пройти тест что-то пошло не так, тест не найден");
                return "redirect:/";
            }
        }

        // подсчет количества неправильных ответов
        Double wrongAnswer = numberOfIncorrectAnswersToAQuestion(newCheckedItems, answerTest);

        double result = testResult(Double.valueOf(questions.size()), wrongAnswer);
        answerTest.setResult((int) result);
        answerTestService.save(answerTest);
        model.addAttribute("answerTest", answerTest);
        return "test/resultPage";
    }


    public Double numberOfIncorrectAnswersToAQuestion(Multimap<Long, Long> newCheckedItems, AnswerTest answerTest) {
        Double wrongAnswer = 0.0;
        Set keySet = newCheckedItems.keySet();
        Iterator keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            Long key = (Long) keyIterator.next();
            List<Long> values = (List<Long>) newCheckedItems.get(key);
            List<PossibleAnswer> CountOfPossibleAnswer = possibleAnswerService.findAllPossibleAnswersByQuestionId(key);
            List<PossibleAnswer> CountOfCorrectPossibleAnswers = new ArrayList<>();
            for (PossibleAnswer possibleAnswer : CountOfPossibleAnswer) {
                if (possibleAnswer.getCorrectAnswer() == 1) {
                    CountOfCorrectPossibleAnswers.add(possibleAnswer);
                }
            }

            List<PossibleAnswer> allUserAnswersToOneQuestion = new ArrayList<>();

            for (Long value : values) {
                AnswerQuestion answerQuestion = new AnswerQuestion();
                PossibleAnswer possibleAnswer = possibleAnswerService.findById(value);
                allUserAnswersToOneQuestion.add(possibleAnswer);
                answerQuestion.setPossibleAnswer(possibleAnswer);
                answerQuestion.setAnswerTest(answerTest);
                answerQuestionService.save(answerQuestion);
            }
            System.out.println(CountOfCorrectPossibleAnswers.size() +" " +  allUserAnswersToOneQuestion.size());
            if (CountOfCorrectPossibleAnswers.size() == allUserAnswersToOneQuestion.size()) {
                for (int i = 0; i < CountOfCorrectPossibleAnswers.size(); i++) {
                    if (CountOfCorrectPossibleAnswers.get(i).getId() != (allUserAnswersToOneQuestion.get(i).getId())) {
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
            double newDouble2 = new BigDecimal(result).setScale(0, RoundingMode.HALF_EVEN).doubleValue();
            return newDouble2;
        }
        return result = 100.0;
    }
}
