package com.example.blog.service;

import com.example.blog.dao.entities.Post;

import java.util.List;

public interface PostManager {

    Post createPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
}
