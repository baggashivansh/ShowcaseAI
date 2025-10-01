package com.showcaseai.controller;

import com.showcaseai.model.User;
import com.showcaseai.service.CerebrasService;
import com.showcaseai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ShowcaseController {

    @Autowired
    private CerebrasService cerebrasService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/profile/{slug}")
    public String viewProfile(@PathVariable String slug, Model model) {
        User user = userService.findByProfileSlug(slug);
        if (user == null) {
            return "error";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/api/create-profile")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createProfile(@RequestBody Map<String, String> request) {
        try {
            User user = new User();
            user.setFullName(request.get("fullName"));
            user.setEmail(request.get("email"));
            user.setPhone(request.get("phone"));
            user.setLinkedinUrl(request.get("linkedinUrl"));
            user.setGithubUrl(request.get("githubUrl"));
            user.setPortfolioUrl(request.get("portfolioUrl"));
            user.setOriginalResume(request.get("resumeText"));
            user.setSkills(request.get("skills"));
            user.setEducation(request.get("education"));
            user.setWorkExperience(request.get("workExperience"));
            user.setProjects(request.get("projects"));
            user.setAchievements(request.get("achievements"));

            String slug = generateSlug(user.getFullName());
            user.setProfileSlug(slug);

            user.setEnhancedBio(cerebrasService.enhanceProfessionalSummary(user.getOriginalResume()));
            // TODO: Call other CerebrasService enhancements here if implemented

            userService.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("profileUrl", "/profile/" + slug);
            response.put("message", "Profile created successfully!");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create profile: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private String generateSlug(String fullName) {
        if (fullName == null) {
            return null;
        }
        return fullName.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");
    }
}
