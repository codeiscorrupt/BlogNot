package com.example.blog.dao.repositories;

import com.example.blog.dao.entities.Post;
import com.example.blog.dao.entities.React;
import com.example.blog.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactRepo extends JpaRepository<React, Long> {
    React getAllById(Long id);

    React getOneByPostAndReactor(Post post, User user);

    Post getPostById(Long id);
}
