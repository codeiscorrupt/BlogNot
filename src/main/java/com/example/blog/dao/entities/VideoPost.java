package com.example.blog.dao.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("VIDEO")
public class VideoPost extends Post {

    @Getter
    @Setter
    private String url;

}
