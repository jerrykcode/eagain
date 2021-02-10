package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.dto.NotificationDTO;
import com.jerrykcode.eagain.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    void addNotification(Notification notification);
    List<NotificationDTO> unreadNotifications(Long receiverId);
    List<NotificationDTO> readNotifications(Long receiverId, Integer pageNo, Integer numPerPage);
    void readAll(Long receiverId);
}
