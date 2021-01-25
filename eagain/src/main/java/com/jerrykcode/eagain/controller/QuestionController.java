package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.dto.QuestionDTO;
import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.request.NewQuestionRequest;
import com.jerrykcode.eagain.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/new")
    public Long newQuestion(@RequestBody NewQuestionRequest newQuestionRequest) {
        return questionService.create(
                new QuestionDTO()
                   .setQuestion(
                        new Question().setCreatorId(newQuestionRequest.getCreatorId())
                           .setTitle(newQuestionRequest.getTitle())
                           .setContent(newQuestionRequest.getContent())
                           .setGmtCreate(System.currentTimeMillis())
                           .setGmtModified(System.currentTimeMillis())
                    )
                    .setTagIds(Arrays.asList(newQuestionRequest.getTagIds().clone()))
                );
    }

}
