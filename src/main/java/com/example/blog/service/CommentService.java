package com.example.blog.service;

import com.example.blog.dao.entities.Comment;
import com.example.blog.dao.entities.Post;
import com.example.blog.dao.repositories.CommentRepository;
import com.example.blog.dao.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements CommentManager{

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long id){
        return commentRepository.getCommentsById(id);
    }

    @Override
    public Comment updateComment(Long id, Comment comment){
        Comment ta3li9 = commentRepository.getCommentsById(id);
        ta3li9.setAuthor(comment.getAuthor());
        ta3li9.setPost(comment.getPost());
        ta3li9.setContent(comment.getContent());

        return commentRepository.save(ta3li9);
    }

    @Override
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

}
