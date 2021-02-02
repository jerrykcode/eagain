package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.mapper.AnswerMapper;
import com.jerrykcode.eagain.model.Answer;
import com.jerrykcode.eagain.service.AnswerService;
import com.jerrykcode.eagain.service.AnswersCountService;
import com.jerrykcode.eagain.service.views.ViewsCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private ViewsCountService viewsCountService;

    @Autowired
    private AnswersCountService answersCountService;

    @Override
    public void create(Answer answer) {
        //answersCountService.increaseAnswersCount(answer.getQuestionId());
        answerMapper.create(answer);
    }

    @Override
    public List<Answer> listByQuestionId(Long questionId, Integer pageNo, Integer numPerPage) {
        Integer offset = (pageNo - 1) * numPerPage;
        List<Answer> answers = answerMapper.listByQuestionId(questionId, numPerPage, offset);
        answers.forEach(answer -> {
            answer.setViewsCount(viewsCountService.increaseViewsCount(DBModelEnum.DB_ANSWER, ""+answer.getId()).intValue());
        });
        return answers;
    }
}
