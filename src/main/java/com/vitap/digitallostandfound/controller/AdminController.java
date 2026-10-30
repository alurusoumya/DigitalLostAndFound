package com.vitap.digitallostandfound.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vitap.digitallostandfound.entity.Claim;
import com.vitap.digitallostandfound.entity.Notification;
import com.vitap.digitallostandfound.repository.ClaimRepository;
import com.vitap.digitallostandfound.repository.ItemRepository;
import com.vitap.digitallostandfound.repository.NotificationRepository;
import com.vitap.digitallostandfound.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalItems", itemRepository.count());
        model.addAttribute("totalClaims", claimRepository.count());

        return "admin-dashboard";
    }

    @GetMapping("/admin-claims")
    public String viewClaims(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("claims", claimRepository.findAll());
        return "admin-claims";
    }

    @GetMapping("/admin-items")
    public String adminItems(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("items", itemRepository.findAll());
        return "admin-items";
    }

    @GetMapping("/admin-users")
    public String manageUsers(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }

    @GetMapping("/approve-claim/{id}")
    public String approveClaim(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        Claim claim = claimRepository.findById(id).orElse(null);

        if (claim != null) {
            claim.setClaimStatus("Approved");
            claimRepository.save(claim);

            Notification notification = new Notification();
            notification.setUserId(claim.getUserId());
            notification.setMessage("Your claim for Item ID " + claim.getItemId() + " has been approved.");
            notification.setNotificationDate(LocalDate.now());
            notification.setStatus("Unread");

            notificationRepository.save(notification);
        }

        return "redirect:/admin-claims";
    }

    @GetMapping("/reject-claim/{id}")
    public String rejectClaim(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        Claim claim = claimRepository.findById(id).orElse(null);

        if (claim != null) {
            claim.setClaimStatus("Rejected");
            claimRepository.save(claim);

            Notification notification = new Notification();
            notification.setUserId(claim.getUserId());
            notification.setMessage("Your claim for Item ID " + claim.getItemId() + " has been rejected.");
            notification.setNotificationDate(LocalDate.now());
            notification.setStatus("Unread");

            notificationRepository.save(notification);
        }

        return "redirect:/admin-claims";
    }
}