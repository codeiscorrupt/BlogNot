package com.example.blog.service;

import com.example.blog.dao.entities.React;

import java.util.List;

public interface ReactManager {

    React createReact(React react);
    List<React> getAllReacts();
    React getReactById(Long id);
    React updateReact(Long id, React react);
    void deleteReact(Long id);

}
