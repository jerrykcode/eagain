package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.dto.AnswerDTO;
import com.jerrykcode.eagain.model.Answer;
import com.jerrykcode.eagain.request.NewAnswerRequest;
import com.jerrykcode.eagain.service.AnswerService;
import com.jerrykcode.eagain.service.cache.impl.Id2UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private Id2UsernameService id2UsernameService;

    @PostMapping("/new")
    public void newAnswer(@RequestBody NewAnswerRequest newAnswerRequest) {
        Answer answer = new Answer();
        answer.setQuestionId(newAnswerRequest.getQuestionId())
                .setQuestionTitle(newAnswerRequest.getQuestionTitle())
                .setCreatorId(newAnswerRequest.getCreatorId())
                .setContent(newAnswerRequest.getContent())
                .setGmtCreate(System.currentTimeMillis())
                .setGmtModified(answer.getGmtCreate());
        answerService.create(answer);
    }

    @GetMapping("/list")
    public List<AnswerDTO> listByQuestionId(@RequestParam("questionId")Long questionId,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1")Integer pageNo,
                                            @RequestParam(value = "numPerPage", required = false, defaultValue = "5")Integer numPerPage) {
        List<Answer> answers = answerService.listByQuestionId(questionId, pageNo, numPerPage);
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        answers.forEach(answer -> {
            answerDTOS.add(
                    new AnswerDTO()
                    .setAnswer(answer)
                    .setCreatorName(id2UsernameService.id2Username(answer.getCreatorId())));
        });
        return answerDTOS;
    }

    @GetMapping("/query")
    public AnswerDTO queryById(@RequestParam("answerId")Long answerId) {
        Answer answer = answerService.queryById(answerId);
        return new AnswerDTO()
                .setAnswer(answer)
                .setCreatorName(id2UsernameService.id2Username(answer.getCreatorId()));
    }
}
