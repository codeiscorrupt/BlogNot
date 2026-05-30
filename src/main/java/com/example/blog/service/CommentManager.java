package com.example.blog.service;

import com.example.blog.dao.entities.Comment;
import java.util.List;

public interface CommentManager {

    Comment createComment(Comment comment);
    List<Comment> getAllComments();
    Comment getCommentById(Long id);
    Comment updateComment(Long id, Comment comment);
    void deleteComment(Long id);

}
