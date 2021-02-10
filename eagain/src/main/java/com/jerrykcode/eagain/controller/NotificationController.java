package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.dto.NotificationDTO;
import com.jerrykcode.eagain.service.NotificationService;
import com.jerrykcode.eagain.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/unread")
    public List<NotificationDTO> listUnreadNotifications(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        Long receiverId = JwtUtils.getUserId(jwtToken);
        return notificationService.unreadNotifications(receiverId);
    }

    @GetMapping("/read")
    public List<NotificationDTO> listReadNotifications(@RequestParam("pageNo") Integer pageNo,
                                                       @RequestParam("numPerPage") Integer numPerPage,
                                                       HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        Long receiverId = JwtUtils.getUserId(jwtToken);
        return notificationService.readNotifications(receiverId, pageNo, numPerPage);
    }

    @GetMapping("/handle")
    public void handle(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        Long receiverId = JwtUtils.getUserId(jwtToken);
        notificationService.readAll(receiverId);
    }
}
