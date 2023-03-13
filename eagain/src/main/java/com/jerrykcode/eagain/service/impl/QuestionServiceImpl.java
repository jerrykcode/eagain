package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.dto.HomePageQuestionDTO;
import com.jerrykcode.eagain.dto.QuestionDTO;
import com.jerrykcode.eagain.dto.page.QuestionPage;
import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.mapper.QuestionMapper;
import com.jerrykcode.eagain.mapper.QuestionTagMapper;
import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.service.QuestionService;
import com.jerrykcode.eagain.service.likes.LikesCountService;
import com.jerrykcode.eagain.service.cache.impl.Id2UsernameService;
import com.jerrykcode.eagain.service.views.ViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionTagMapper questionTagMapper;

    @Autowired
    private Id2UsernameService id2UsernameService;

    @Autowired
    private ViewsService viewsService;

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
    public Question queryById(Long questionId) {
        return questionMapper.queryById(questionId);
    }

    @Override
    public QuestionPage getQuestionPage(Long questionId, Long userId) {
        Question question = questionMapper.queryById(questionId);
        return new QuestionPage()
                .setCreatorName(id2UsernameService.id2Username(question.getCreatorId()))
                .setTitle(question.getTitle())
                .setContent(question.getContent())
                .setGmtCreate(question.getGmtCreate())
                .setGmtModified(question.getGmtModified())
                .setAnswersCount(question.getAnswersCount())
                .setViewsCount(viewsService.increaseViewsCountNoDup(DBModelEnum.DB_QUESTION, "" + questionId, userId).intValue())
                .setLikesCount(likesCountService.getLikesCount(DBModelEnum.DB_QUESTION, "" + questionId).intValue())
                .setFocusesCount(question.getFocusesCount())
                .setTags(questionTagMapper.listByQuestionId(questionId));
    }

    @Override
    public List<HomePageQuestionDTO> listHomePageQuestions(Integer pageNo, Integer numPerPage) {
        Integer limit = numPerPage;
        Integer offset = (pageNo - 1) * numPerPage;
        List<Question> questions = questionMapper.list(limit, offset);
        List<HomePageQuestionDTO> homePageQuestionDTOS = new ArrayList<>();
        questions.forEach(question -> {
            HomePageQuestionDTO homePageQuestionDTO = new HomePageQuestionDTO()
                    .setQuestionId(question.getId())
                    .setCreatorName(id2UsernameService.id2Username(question.getCreatorId()))
                    .setTitle(question.getTitle())
                    .setBreviary(getBreviary(question.getContent()))
                    .setGmtCreate(question.getGmtCreate())
                    .setGmtModified(question.getGmtModified())
                    .setAnswersCount(question.getAnswersCount())
                    .setViewsCount(viewsService.getViewsCountNoDup(DBModelEnum.DB_QUESTION, "" + question.getId()).intValue())
                    .setLikesCount(likesCountService.getLikesCount(DBModelEnum.DB_QUESTION, "" + question.getId()).intValue())
                    .setFocusesCount(question.getFocusesCount());
            homePageQuestionDTOS.add(homePageQuestionDTO);
        });
        return homePageQuestionDTOS;
    }

    @Override
    public Long countAllQuestions() {
        return questionMapper.countAll();
    }

    private String getBreviary(String  content) {
        if (content.length() < HomePageQuestionDTO.BREVIARY_SIZE)
            return content;
        return content.substring(0, HomePageQuestionDTO.BREVIARY_SIZE);
    }
}
