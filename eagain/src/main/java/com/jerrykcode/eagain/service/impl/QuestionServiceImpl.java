package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.dto.QuestionDTO;
import com.jerrykcode.eagain.dto.page.QuestionPage;
import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.mapper.QuestionMapper;
import com.jerrykcode.eagain.mapper.QuestionTagMapper;
import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.service.QuestionService;
import com.jerrykcode.eagain.service.likes.LikesCountService;
import com.jerrykcode.eagain.service.views.ViewsCountService;
import com.jerrykcode.eagain.service.cache.impl.Id2UsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionTagMapper questionTagMapper;

    @Autowired
    private Id2UsernameService id2UsernameService;

    @Autowired
    private ViewsCountService viewsCountService;

    @Autowired
    private LikesCountService likesCountService;

    @Override
    public Long create(QuestionDTO questionDTO) {
        questionMapper.create(questionDTO.getQuestion());
        Long questionId = questionDTO.getQuestion().getId();
        questionDTO.getTagIds().forEach(tagId->{
            questionTagMapper.insert(questionId, tagId);
        });
        return questionId;
    }

    @Override
    public QuestionPage queryById(Long questionId) {
        Question question = questionMapper.queryById(questionId);
        return new QuestionPage()
                .setCreatorName(id2UsernameService.id2Username(question.getCreatorId()))
                .setTitle(question.getTitle())
                .setContent(question.getContent())
                .setGmtCreate(question.getGmtCreate())
                .setGmtModified(question.getGmtModified())
                .setAnswersCount(question.getAnswersCount())
                .setViewsCount(viewsCountService.increaseViewsCount(DBModelEnum.DB_QUESTION, "" + questionId).intValue())
                .setLikesCount(likesCountService.getLikesCount(DBModelEnum.DB_QUESTION, "" + questionId).intValue())
                .setFocusesCount(question.getFocusesCount())
                .setTags(questionTagMapper.listByQuestionId(questionId));
    }
}
