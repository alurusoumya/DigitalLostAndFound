package com.vitap.digitallostandfound.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vitap.digitallostandfound.entity.Claim;
import com.vitap.digitallostandfound.entity.User;
import com.vitap.digitallostandfound.repository.ClaimRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClaimController {

    @Autowired
    private ClaimRepository claimRepository;

    @GetMapping("/claim-item/{itemId}")
    public String claimPage(@PathVariable int itemId, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("itemId", itemId);
        return "claim-item";
    }

    @PostMapping("/submit-claim")
    public String submitClaim(@RequestParam int itemId,
                              @RequestParam String claimantName,
                              @RequestParam String claimantEmail,
                              @RequestParam String claimantPhone,
                              HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/";
        }

        Claim claim = new Claim();
        claim.setItemId(itemId);
        claim.setUserId(user.getUserId());
        claim.setClaimantName(claimantName);
        claim.setClaimantEmail(claimantEmail);
        claim.setClaimantPhone(claimantPhone);
        claim.setClaimDate(LocalDate.now());
        claim.setClaimStatus("Pending");

        claimRepository.save(claim);

        return "redirect:/items";
    }
}