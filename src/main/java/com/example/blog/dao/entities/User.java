package com.example.blog.dao.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();


    @Getter
    @Setter
    @OneToOne
    @JoinColumn
    private Profile profile;


    private final boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return this.enabled; }

    @JsonManagedReference
    @OneToMany(mappedBy = "reactor", cascade = CascadeType.ALL)
    private List<React> reacts = new ArrayList<>();



    // Constructors
    public User() {}

    public User(String username, String email, String password, Profile profile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile = profile;}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile = new Profile();}


    public Long getId()          { return id; }
    public String getUsername()     { return username; }
    public String getEmail()   { return email; }
    public List<Post> getPosts() { return posts; }
    public Integer getPostsCount() { return posts.size(); }
    public String getPassword() { return password; }

    // Setters
    public void setId(Long id)              { this.id = id; }
    public void setUsername(String username)      { this.username = username; }
    public void setEmail(String email)  { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}