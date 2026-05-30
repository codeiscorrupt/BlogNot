package com.example.blog.web;

import com.example.blog.dao.entities.*;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
public class PostWebController {

    private final PostService postService;
    private final UserService userService;
    private final TagService tagService;

    @Autowired
    public PostWebController(PostService postService, UserService userService, TagService tagService) {
        this.postService = postService;
        this.userService = userService;
        this.tagService = tagService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model, Authentication authentication ) {
        User loggedInUser = (User) authentication.getPrincipal();
        model.addAttribute("tagslist", tagService.getAllTagsOrderedByPostCount());
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("posts", postService.getAllPosts());
        return "posts/list";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model, Authentication authentication ) {
        User loggedInUser = (User) authentication.getPrincipal();
        Comment comment = new Comment();
        React react = new React();
        model.addAttribute("comment", comment);
        model.addAttribute("react", react);
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("user", postService.getPostById(id).getAuthor());
        return "posts/view";
    }

    @GetMapping("/posts/new")
    public String newPostForm(Model model, Authentication authentication ) {
        User loggedInUser = (User) authentication.getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("post", new Post());
        return "posts/edit";
    }

    @PostMapping("/posts")
    public String createPost(@ModelAttribute Post post, Authentication authentication, @RequestParam("tagz") String tagz, @RequestParam("bideo") MultipartFile videoFile) {

        User loggedInUser = (User) authentication.getPrincipal();

        // Traitement d tags
        String[] smiya = tagz.split(",");
        for (String tag : smiya){
            int check = 0;
            for (Tag dbtag : tagService.getAllTags()) {
                if (dbtag.getName().equals(tag.strip())) {
                    List<Tag> newlist = post.getTags();
                    newlist.add(dbtag);
                    post.setTags(newlist);
                    check = 1;
                }
            }
            if (check == 0){
                Tag newtag = new Tag();
                newtag.setName(tag.strip());
                tagService.createTag(newtag);
                List<Tag> newlist = post.getTags();
                newlist.add(newtag);
                post.setTags(newlist);
            }
        }

        post.setAuthor(loggedInUser);

        if (!videoFile.isEmpty()) {
            VideoPost video = new VideoPost();

            video.setId(post.getId());
            video.setContent(post.getContent());
            video.setTags(post.getTags());
            video.setTitle(post.getTitle());
            video.setAuthor(loggedInUser);


            String filePath = saveFileToDisk(videoFile);

            video.setUrl(filePath);
            postService.createPost(video);
            return "redirect:/posts";
        }

        postService.createPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model, Authentication authentication ) {
        User loggedInUser = (User) authentication.getPrincipal();
        model.addAttribute("loggedInUser", loggedInUser);

        if(!(postService.getPostById(id).getAuthor().getId().equals(loggedInUser.getId()))) {
           return "redirect:/posts";
        }

        model.addAttribute("post", postService.getPostById(id));

        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post, @RequestParam("tagz") String tagz) {
        String[] smiya = tagz.split(",");

        for (String tag : smiya){
            int check = 0;
            for (Tag dbtag : tagService.getAllTags()) {
                if (dbtag.getName().equals(tag.strip())) {
                    List<Tag> newlist = post.getTags();
                    newlist.add(dbtag);
                    post.setTags(newlist);
                    check = 1;
                }
            }
            if (check == 0){
                Tag newtag = new Tag();
                newtag.setName(tag.strip());
                tagService.createTag(newtag);
                List<Tag> newlist = post.getTags();
                newlist.add(newtag);
                post.setTags(newlist);
            }
        }

        postService.updatePost(id, post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, Authentication authentication) {

        Post post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/posts?error=notfound";
        }


        User loggedInUser = (User) authentication.getPrincipal();


        if (!post.getAuthor().getId().equals(loggedInUser.getId())) {
            return "redirect:/posts?error=unauthorized";
        }

        postService.deletePost(id);

        return "redirect:/posts";
    }

    private String saveFileToDisk(MultipartFile file) {

        String uploadDir = "uploads/videos/"+ UUID.randomUUID().toString()+file.getOriginalFilename();

        Path uploadPath = Paths.get(uploadDir);

        try {
            Files.createDirectories(uploadPath);

        } catch (IOException e) {
            throw new RuntimeException("Cannot create upload directory: " + e.getMessage(), e);
        }

        try {
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File (Video) saved successfully to: " + uploadPath);

            return "\\"+uploadPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }
    }

}
