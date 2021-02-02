package com.jerrykcode.eagain.service.views.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.service.views.DBViewServiceFactory;
import com.jerrykcode.eagain.service.views.DBViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBViewServiceFactoryImpl implements DBViewServiceFactory {

    @Autowired
    private QuestionDBViewsService questionDBViewsService;

    @Autowired
    private AnswerDBViewsService answerDBViewsService;

    @Override
    public DBViewsService getDBViewService(String modelName) {
        if (modelName.equals(DBModelEnum.DB_QUESTION.getName()))
            return questionDBViewsService;
        if (modelName.equals(DBModelEnum.DB_ANSWER.getName()))
            return answerDBViewsService;
        return null;
    }
}
