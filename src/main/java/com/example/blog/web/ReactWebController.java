package com.example.blog.web;

import com.example.blog.dao.entities.Post;
import com.example.blog.dao.entities.React;
import com.example.blog.dao.entities.User;
import com.example.blog.dao.repositories.ReactRepo;
import com.example.blog.service.PostService;
import com.example.blog.service.ReactService;
import com.example.blog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReactWebController {

    private final ReactService reactService;
    private final PostService postService;
    private final UserService userService;

    public ReactWebController(ReactService reactService, PostService postService, UserService userService){
        this.reactService = reactService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/reacts")
    public String react(Authentication authentication, @RequestParam String reactype, @RequestParam Long post_id){
        User loggedInUser = (User) authentication.getPrincipal();
        Post post = postService.getPostById(post_id);

        React react = reactService.getReactByRnP(loggedInUser,post);

        if (react == null){
            react = new React();
            react.setReactor(loggedInUser);
            react.setPost(post);
            react.setType(reactype);

            reactService.createReact(react);
            return "redirect:/posts/"+ post.getId().toString();
        }

        if (react.getType().equals(reactype)){
            reactService.deleteReact(react.getId());
        }

        else {
            react.setType(reactype);
            reactService.updateReact(react.getId(), react);

        }
        return "redirect:/posts/"+ post.getId().toString();
    }
}
