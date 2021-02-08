package com.jerrykcode.eagain.service.likes.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.service.likes.DBLikesService;
import com.jerrykcode.eagain.service.likes.DBLikesServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBLikesServiceFactoryImpl implements DBLikesServiceFactory {

    @Autowired
    private QuestionDBLikesService questionDBLikesService;

    @Autowired
    private AnswerDBLikesService answerDBLikesService;

    @Override
    public DBLikesService getDBLikesService(String dbModelName) {
        if (dbModelName.equals(DBModelEnum.DB_QUESTION.getName()))
            return questionDBLikesService;
        else if (dbModelName.equals(DBModelEnum.DB_ANSWER.getName()))
            return answerDBLikesService;
        else
            return null;
    }
}
