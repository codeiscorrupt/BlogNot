package com.example.blog.web;

import com.example.blog.dao.entities.Comment;
import com.example.blog.dao.entities.Post;
import com.example.blog.dao.entities.Tag;
import com.example.blog.dao.entities.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
public class CommentWebController {

    private final CommentService commentService;
    private final PostService postService;


    @Autowired
    public CommentWebController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/comment")
    public String createComment(@ModelAttribute Comment comment, Authentication authentication,@RequestParam Long post_id) {
        User loggedInUser = (User) authentication.getPrincipal();

        comment.setAuthor(loggedInUser);
        comment.setPost(postService.getPostById(post_id));
        commentService.createComment(comment);
        String redirect = String.join("","redirect:/posts/", comment.getPost().getId().toString());
        return redirect;
    }

    @GetMapping("/comments/{id}/delete")
    public String deleteComment(@PathVariable Long id, Authentication authentication) {

        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return "redirect:/posts?error=commentnotfound";
        }

        User loggedInUser = (User) authentication.getPrincipal();
        if (!comment.getAuthor().getId().equals(loggedInUser.getId())) {
            return "redirect:/posts?error=unauthorized";
        }

        Long postId = comment.getPost().getId();
        commentService.deleteComment(id);

        return "redirect:/posts/" + postId;
    }

}
