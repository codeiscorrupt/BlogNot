package com.example.blog.web;  // ← matches YOUR package

import com.example.blog.dao.entities.Profile;
import com.example.blog.dao.entities.User;
import com.example.blog.dao.repositories.UserRepository;
import com.example.blog.service.PostService;
import com.example.blog.service.ProfileService;
import com.example.blog.service.ReactService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.blog.security.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
public class UserWebController {

    private final PostService postService;
    private final UserService userService;
    private final ProfileService profileService;
    private final ReactService reactService;

    @Autowired
    public UserWebController(PostService postService, UserService userService, ProfileService profileService, ReactService reactService) {
        this.postService = postService;
        this.userService = userService;
        this.profileService = profileService;
        this.reactService = reactService;
    }

    private PasswordEncoder passwordEncoder = SecurityConfig.passwordEncoder();


    /// Registration
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setProfile(profileService.createProfile(new Profile()));
        userService.createUser(user);

        return "redirect:/login";
    }

    /// login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

//    @GetMapping("/users")
//    public String listUsers(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "users/list";
//    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model, Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("user", userService.getUserById(id));
        return "users/view";
    }

//    @GetMapping("/users/new")
//    public String newUserForm(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("users", userService.getAllUsers());
//        return "users/edit";
//    }

//    @PostMapping("/users")
//    public String createUser(@ModelAttribute User user) {
//        userService.createUser(user);
//        return "redirect:/users";
//    }

    @GetMapping("/users/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model, Authentication authentication) {

        User loggedInUser = (User) authentication.getPrincipal();

        if (!(id.equals(loggedInUser.getId()))) {
            return "redirect:/users";
        }

        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user,
                             @RequestParam("avatar") MultipartFile avatarFile,
                             @RequestParam String bio) {

        Profile profile = profileService.getProfileByID(userService.getUserById(id).getProfile().getId());

        if (!avatarFile.isEmpty()) {
            String filePath = saveFileToDisk(avatarFile);
            profile.setAvatar(filePath);
        }

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));

        profile.setBio(bio);
        profileService.updateProfile(userService.getUserById(id).getProfile().getId(), profile);
        user.setProfile(profile);
        userService.updateUser(id, user);
        return "redirect:/posts";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        profileService.deleteProfile(userService.getUserById(id).getProfile().getId());
        userService.deleteUser(id);
        return "redirect:/users";
    }


    private String saveFileToDisk(MultipartFile file) {

        String uploadDir = "uploads/avatars/"+UUID.randomUUID().toString()+file.getOriginalFilename();

        Path uploadPath = Paths.get(uploadDir);

        try {
            Files.createDirectories(uploadPath);

        } catch (IOException e) {
            throw new RuntimeException("Cannot create upload directory: " + e.getMessage(), e);
        }

        try {
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved successfully to: " + uploadPath);

            return "\\"+uploadPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }
    }
}