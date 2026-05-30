package com.example.blog.dao.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profiles")
public class Profile {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column
    private String bio;

    @Getter
    @Setter
    @Column
    private String avatar = "\\uploads\\avatars\\default.jpg";

    @Getter
    @Setter
    @OneToOne(mappedBy = "profile")
    private User user;


}
