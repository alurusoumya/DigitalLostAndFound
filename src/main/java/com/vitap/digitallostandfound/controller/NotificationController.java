package com.vitap.digitallostandfound.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vitap.digitallostandfound.entity.Notification;
import com.vitap.digitallostandfound.entity.User;
import com.vitap.digitallostandfound.repository.NotificationRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String notifications(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/";
        }

        List<Notification> notifications =
                notificationRepository.findByUserId(user.getUserId());

        // Mark all unread notifications as Read
        for (Notification n : notifications) {
            if ("Unread".equalsIgnoreCase(n.getStatus())) {
                n.setStatus("Read");
                notificationRepository.save(n);
            }
        }

        model.addAttribute("notifications", notifications);

        return "notifications";
    }
}