package com.example.blog.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")

public class Tag {

    @Id
    @Getter
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column
    private String name;

    @Getter
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public Integer getPostsCount(){
        return posts.size();}
}
