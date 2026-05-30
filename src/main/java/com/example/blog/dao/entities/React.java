package com.example.blog.dao.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reactions")
public class React {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    private String type;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "reactor_id")
    private User reactor;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void setType(String type) {
        if (type.equalsIgnoreCase("like") ||
                type.equalsIgnoreCase("haha") ||
                type.equalsIgnoreCase("love") ||
                type.equalsIgnoreCase("wow")
        ){
            this.type = type;
        }
        else{
        this.type = "like";
        }
    }
}
