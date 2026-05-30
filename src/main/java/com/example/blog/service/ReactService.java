package com.example.blog.service;

import com.example.blog.dao.entities.Post;
import com.example.blog.dao.entities.React;
import com.example.blog.dao.entities.User;
import com.example.blog.dao.repositories.PostRepository;
import com.example.blog.dao.repositories.ReactRepo;
import com.example.blog.dao.repositories.UserRepository;
import com.example.blog.service.PostService;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReactService implements ReactManager{

    private final ReactRepo reactRepo;
    private final PostService postService;
    private final UserService userService;

    public ReactService(ReactRepo reactRepo, PostService postService, UserService userService){
        this.reactRepo = reactRepo;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public React createReact(React react) {
        return reactRepo.save(react);
    }

    @Override
    public List<React> getAllReacts() {
        return reactRepo.findAll();
    }

    @Override
    public React getReactById(Long id) {
        return reactRepo.getAllById(id);
    }

    @Override
    public React updateReact(Long id, React react) {
        React tafa3ol = getReactById(id);
        tafa3ol.setPost(react.getPost());
        tafa3ol.setReactor(react.getReactor());
        tafa3ol.setType(react.getType());

        return reactRepo.save(tafa3ol);
    }


    @Override
    public void deleteReact(Long id) {
        reactRepo.delete(getReactById(id));
    }


    public React getReactByRnP(User user_id, Post post_id ){
        React react = reactRepo.getOneByPostAndReactor(post_id,user_id);
        return react;
    }


}
