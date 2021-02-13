package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.dto.HomePageQuestionDTO;
import com.jerrykcode.eagain.dto.QuestionDTO;
import com.jerrykcode.eagain.dto.page.QuestionPage;
import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.request.NewQuestionRequest;
import com.jerrykcode.eagain.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/questions")
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

    @GetMapping("/query/{id}")
    public QuestionPage queryById(@PathVariable("id")Long id) {
        return questionService.getQuestionPage(id);
    }

    @GetMapping("/list")
    public List<HomePageQuestionDTO> list(@RequestParam(value = "pageNo", required = false, defaultValue = "1")Integer pageNo,
                                          @RequestParam(value = "numPerPage", required = false, defaultValue = "10")Integer numPerPage) {
        return questionService.listHomePageQuestions(pageNo, numPerPage);
    }

    @GetMapping("/count/all")
    public Long countAllQuestions() {
        return questionService.countAllQuestions();
    }

}
