package com.example.blog.service;

import com.example.blog.dao.entities.Tag;
import com.example.blog.dao.repositories.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService implements TagManager{

    private final TagRepo tagRepo;

    public TagService(TagRepo tagRepo){
        this.tagRepo = tagRepo;
    }

    @Override
    public Tag createTag(Tag tag){
        return tagRepo.save(tag);
    }

    @Override
    public List<Tag> getAllTags(){
        return tagRepo.findAll();
    }

    @Override
    public Tag getTagById(Long id){ return tagRepo.getTagsById(id); }

    @Override
    public Tag updateTag(Long id, Tag tag){
        Tag tag9dim = getTagById(id);
        tag9dim.setName(tag.getName());
        return tagRepo.save(tag9dim);
    }

    @Override
    public void deleteTag(Long id){
        tagRepo.deleteById(id);
    }

    public List<Tag> getAllTagsOrderedByPostCount() {
        List<Tag> tags = tagRepo.findAll();

        // Sort by posts list size (descending - most posts first)
        tags.sort((tag1, tag2) -> {
            int size1 = tag1.getPosts() != null ? tag1.getPosts().size() : 0;
            int size2 = tag2.getPosts() != null ? tag2.getPosts().size() : 0;
            return Integer.compare(size2, size1); // Descending order
        });

        return tags;
    }

}
