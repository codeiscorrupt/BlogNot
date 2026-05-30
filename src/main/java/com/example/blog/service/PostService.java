package com.example.blog.service;

import com.example.blog.dao.entities.Post;
import com.example.blog.dao.entities.Tag;
import com.example.blog.dao.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.blog.service.CommentService;
import com.example.blog.dao.entities.Comment;

@Service
public class PostService implements PostManager {

    private final PostRepository postRepository;
    private final CommentService commentService;

    public PostService(PostRepository postRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            Collections.reverse(list);
                            return list;
                        }
                ));
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post updated) {
        Post existing = getPostById(id);
        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        existing.setAuthor(updated.getAuthor());
        existing.setTags(updated.getTags());
        return postRepository.save(existing);
    }


    @Override
    public void deletePost(Long id) {
        for (Comment comment : getPostById(id).getComments()){
            commentService.deleteComment(comment.getId());
        }

        postRepository.deleteById(id);
    }

}