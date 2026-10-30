package com.vitap.digitallostandfound.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vitap.digitallostandfound.entity.Feedback;
import com.vitap.digitallostandfound.entity.User;
import com.vitap.digitallostandfound.repository.FeedbackRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/feedback")
    public String feedbackPage(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        return "feedback";
    }

    @PostMapping("/submit-feedback")
    public String submitFeedback(@RequestParam String message,
                                 HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/";
        }

        Feedback feedback = new Feedback();
        feedback.setUserId(user.getUserId());
        feedback.setMessage(message);
        feedback.setFeedbackDate(LocalDate.now());

        feedbackRepository.save(feedback);

        return "redirect:/user-dashboard";
    }

    @GetMapping("/admin-feedback")
    public String adminFeedback(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("feedbacks", feedbackRepository.findAll());
        return "admin-feedback";
    }
}