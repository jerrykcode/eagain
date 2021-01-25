package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.request.NewQuestionRequest;
import com.jerrykcode.eagain.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/new")
    public Long newQuestion(@RequestBody NewQuestionRequest newQuestionRequest) {
        return questionService.create(
               new Question().setCreatorId(newQuestionRequest.getCreatorId())
                       .setTitle(newQuestionRequest.getTitle())
                       .setContent(newQuestionRequest.getContent())
                       .setGmtCreate(System.currentTimeMillis())
                       .setGmtModified(System.currentTimeMillis())
       );
    }

}
