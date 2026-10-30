package com.vitap.digitallostandfound.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vitap.digitallostandfound.entity.Item;
import com.vitap.digitallostandfound.entity.User;
import com.vitap.digitallostandfound.repository.ItemRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/report-item")
    public String reportItemPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }

        model.addAttribute("item", new Item());
        return "report-item";
    }

    @PostMapping("/save-item")
    public String saveItem(@ModelAttribute Item item,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           HttpSession session) throws IOException {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/";
        }

        item.setDateFound(LocalDate.now());
        item.setUserId(user.getUserId());

        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();

            String uploadPath = System.getProperty("user.dir")
                    + "/src/main/resources/static/uploads/";

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imageFile.transferTo(new File(uploadPath + fileName));
            item.setImagePath(fileName);
        }

        itemRepository.save(item);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String viewItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "view-items";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam String keyword, Model model) {
        model.addAttribute("items",
                itemRepository.findByItemNameContainingIgnoreCase(keyword));
        return "view-items";
    }

    @GetMapping("/filter-category")
    public String filterCategory(@RequestParam String category, Model model) {
        model.addAttribute("items", itemRepository.findByCategory(category));
        return "view-items";
    }

    @GetMapping("/filter-status")
    public String filterStatus(@RequestParam String status, Model model) {
        model.addAttribute("items", itemRepository.findByStatus(status));
        return "view-items";
    }
}