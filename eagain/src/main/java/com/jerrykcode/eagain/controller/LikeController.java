package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.enums.NotificationTypeEnum;
import com.jerrykcode.eagain.model.Notification;
import com.jerrykcode.eagain.service.AnswerService;
import com.jerrykcode.eagain.service.NotificationService;
import com.jerrykcode.eagain.service.QuestionService;
import com.jerrykcode.eagain.service.likes.LikesService;
import com.jerrykcode.eagain.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LikeController {

    @Autowired
    private LikesService likesService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @PostMapping("/like/{dbModelName}/{id}")
    public Integer like(@PathVariable("dbModelName") String dbModelName,
                        @PathVariable("id") String id,
                        HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        Long senderId = JwtUtils.getUserId(jwtToken);
        Long receiverId = null;
        DBModelEnum dbModelEnum = null;
        //change to strategy later
        if (dbModelName.equals("question")) {
            receiverId = questionService.queryById(Long.valueOf(id)).getCreatorId();
            dbModelEnum = DBModelEnum.DB_QUESTION;
        }
        else if (dbModelName.equals("answer")) {
            receiverId = answerService.queryById(Long.valueOf(id)).getCreatorId();
            dbModelEnum = DBModelEnum.DB_ANSWER;
        }
        else throw new IllegalArgumentException();

        notificationService.addNotification(new Notification()
                .setSenderId(senderId)
                .setReceiverId(receiverId)
                .setNotificationTypeEnum(NotificationTypeEnum.NOTIFICATION_TYPE_LIKE)
                .setDbModelEnum(dbModelEnum)
                .setModelId(Long.valueOf(id))
                .setRead(false));

        Long likesCount = likesService.increaseLikesCount(dbModelName, id);
        if (likesCount != null)
            return likesCount.intValue();
        else
            return 0;
    }
}
