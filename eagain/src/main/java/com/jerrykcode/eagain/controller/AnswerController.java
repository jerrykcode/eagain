package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.mapper.AnswerMapper;
import com.jerrykcode.eagain.model.Answer;
import com.jerrykcode.eagain.request.NewAnswerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerMapper answerMapper;

    @PostMapping("/new")
    public void newAnswer(@RequestBody NewAnswerRequest newAnswerRequest) {
        Answer answer = new Answer();
        answer.setQuestionId(newAnswerRequest.getQuestionId())
                .setCreatorId(newAnswerRequest.getCreatorId())
                .setContent(newAnswerRequest.getContent())
                .setGmtCreate(System.currentTimeMillis())
                .setGmtModified(answer.getGmtCreate());
        answerMapper.create(answer);
    }
}
