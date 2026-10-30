package com.vitap.digitallostandfound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vitap.digitallostandfound.entity.User;
import com.vitap.digitallostandfound.repository.ClaimRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyClaimsController {

    @Autowired
    private ClaimRepository claimRepository;

    @GetMapping("/claims")
    public String myClaims(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("claims",
                claimRepository.findByUserId(user.getUserId()));

        return "my-claims";
    }
}