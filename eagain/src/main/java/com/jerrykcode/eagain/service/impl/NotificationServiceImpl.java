package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.dto.NotificationDTO;
import com.jerrykcode.eagain.mapper.NotificationMapper;
import com.jerrykcode.eagain.model.Notification;
import com.jerrykcode.eagain.service.NotificationService;
import com.jerrykcode.eagain.service.cache.impl.Id2UsernameService;
import com.jerrykcode.eagain.util.SerializeUtil;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private Id2UsernameService id2UsernameService;

    @Override
    public void addNotification(Notification notification) {
        if (notification == null || notification.getReceiverId() == null)
            throw new IllegalArgumentException();
        String redisKey = RedisConstants.NOTIFICATION_SET + notification.getReceiverId();
        redisTemplate.opsForSet().add(redisKey.getBytes(), SerializeUtil.serialize(notification));
    }

    @Override
    public List<NotificationDTO> unreadNotifications(Long receiverId) {
        if (receiverId == null)
            throw new IllegalArgumentException();
        String redisKey = RedisConstants.NOTIFICATION_SET + receiverId;
        Set<byte[]> set = redisTemplate.opsForSet().members(redisKey.getBytes());
        List<NotificationDTO> list = new ArrayList<>();
        for (byte[] bytes: set) {
            Object deserialize = SerializeUtil.deserialize(bytes);
            Notification notification = (Notification) deserialize;
            list.add(toDTO(notification));
        }
        return list;
    }

    @Override
    public List<NotificationDTO> readNotifications(Long receiverId, Integer pageNo, Integer numPerPage) {
        if (receiverId == null)
            throw new IllegalArgumentException();
        Integer limit = numPerPage;
        Integer offset = (pageNo - 1) * numPerPage;
        List<Notification> notifications = notificationMapper.list(receiverId, limit, offset);
        List<NotificationDTO> notificationDTOs = new ArrayList<>();
        notifications.forEach(notification -> {
            notificationDTOs.add(toDTO(notification));
        });
        return notificationDTOs;
    }

    @Override
    public void readAll(Long receiverId) {
        if (receiverId == null)
            throw new IllegalArgumentException();
        String redisKey = RedisConstants.NOTIFICATION_SET + receiverId;
        Set<byte[]> set = redisTemplate.opsForSet().members(redisKey.getBytes());
        set.forEach(bytes -> {
            Object deserialize = SerializeUtil.deserialize(bytes);
            notificationMapper.insert((Notification) deserialize);
        });
        redisTemplate.delete(redisKey.getBytes());
    }

    private NotificationDTO toDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO()
                .setSenderId(notification.getSenderId())
                .setSenderName(id2UsernameService.id2Username(notification.getSenderId()))
                .setNotificationType(notification.getNotificationTypeEnum().getName())
                .setDbModelType(notification.getDbModelEnum().getName())
                .setModelId(notification.getModelId())
                .setModelContent("");
        return notificationDTO;
    }
}
