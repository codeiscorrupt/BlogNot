package com.example.blog.dao.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)   // Title is required in DB
    private String content;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    // Constructors
    public Comment() {}

    public Comment(String content, User author, Post post) {
        this.content = content;
        this.author = author;
        this.post = post;
    }

    // Getters
    public Long getId()          { return id; }
    public String getContent()     { return content; }
    public Post getPost()   { return post; }
    public User getAuthor()    { return author; }

    // Setters
    public void setId(Long id)              { this.id = id; }
    public void setContent(String content)      { this.content = content; }
    public void setPost(Post post)  { this.post = post; }
    public void setAuthor(User author)    { this.author = author; }
}