package com.example.blog.dao.repositories;

import com.example.blog.dao.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
    Tag getTagsById(Long id);
}
