package com.example.blog.dao.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity                         // Tells JPA: map this class to a DB table
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("POST")
public class Post {

    @Id                         // This field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;

    @Column(nullable = false)   // Title is required in DB
    private String title;

    @Column(columnDefinition = "TEXT") // Allows long content
    private String content;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;


    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="tag_post",joinColumns = @JoinColumn(name = "post_id")
            , inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<React> reacts = new ArrayList<>();

    public Post() {}

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public List<React> getReactsByType(String type) {
        return reacts.stream().filter(react -> react.getType().equals(type)).collect(Collectors.toList());
    }

    public Long getId()          { return id; }
    public String getTitle()     { return title; }
    public String getContent()   { return content; }
    public User getAuthor()    { return author; }
    public List<Comment> getComments() { return comments; }
    public List<Tag> getTags(){ return tags; }

    public void setId(Long id)              { this.id = id; }
    public void setTitle(String title)      { this.title = title; }
    public void setContent(String content)  { this.content = content; }
    public void setAuthor(User author)    { this.author = author; }
    public void setTags(List<Tag> tags){ this.tags = tags; }

}