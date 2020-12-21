package org.nikita111100.onlinetesting.controller;

import org.nikita111100.onlinetesting.service.AnswerTestService;
import org.springframework.stereotype.Controller;

@Controller
public class AnswerTestController {

    private final AnswerTestService answerTestService;

    public AnswerTestController(AnswerTestService answerTestService) {
        this.answerTestService = answerTestService;
    }
}
