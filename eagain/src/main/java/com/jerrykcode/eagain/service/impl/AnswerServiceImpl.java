package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.mapper.AnswerMapper;
import com.jerrykcode.eagain.mapper.QuestionMapper;
import com.jerrykcode.eagain.model.Answer;
import com.jerrykcode.eagain.service.AnswerService;
import com.jerrykcode.eagain.service.likes.LikesCountService;
import com.jerrykcode.eagain.service.views.ViewsIncrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ViewsIncrService viewsIncrService;

    @Autowired
    private LikesCountService likesCountService;

    @Override
    @Transactional
    public void create(Answer answer) {
        questionMapper.incrAnswersCount(answer.getQuestionId(), 1);
        answerMapper.create(answer);
    }

    @Override
    public Answer queryById(Long answerId) {
        return answerMapper.queryById(answerId);
    }

    @Override
    public List<Answer> listByQuestionId(Long questionId, Integer pageNo, Integer numPerPage) {
        Integer offset = (pageNo - 1) * numPerPage;
        List<Answer> answers = answerMapper.listByQuestionId(questionId, numPerPage, offset);
        answers.forEach(answer -> {
            answer.setViewsCount(viewsIncrService.increaseViewsCount(DBModelEnum.DB_ANSWER, ""+answer.getId()).intValue())
                    .setLikesCount(likesCountService.getLikesCount(DBModelEnum.DB_ANSWER, ""+answer.getId()).intValue());
        });
        return answers;
    }
}
