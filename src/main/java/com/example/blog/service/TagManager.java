package com.example.blog.service;

import com.example.blog.dao.entities.Tag;
import java.util.List;

public interface TagManager {

    Tag createTag(Tag tag);
    List<Tag> getAllTags();
    Tag getTagById(Long id);
    Tag updateTag(Long id, Tag tag);
    void deleteTag(Long id);

}
