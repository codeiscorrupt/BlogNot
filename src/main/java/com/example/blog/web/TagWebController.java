package com.example.blog.web;

import com.example.blog.dao.entities.Tag;
import com.example.blog.dao.entities.User;
import com.example.blog.service.TagService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TagWebController {

    private final TagService tagService;

    public TagWebController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags/{id}")
    public String getTagByID(@PathVariable Long id, Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("loggedInUser", user);
        model.addAttribute("tag",tagService.getTagById(id));
        return "tags/view";
    }

//    @PostMapping("/tags")
//    public String createTag(Tag tag, RedirectAttributes redirectAttributes){
//            tagService.createTag(tag);
//            return "redirect:/tags";
//        }

}
