package com.example.forum.services;

import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Tag;
import com.example.forum.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag create(Tag tag) {
        boolean duplicateExists = true;
        try {
            tagRepository.get(tag.getName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Tag", "name", tag.getName());
        }
        tagRepository.create(tag);
        return tag;
    }

    @Override
    public void update(Tag tag) {
        tagRepository.update(tag);
    }

    @Override
    public void delete(int id) {
        tagRepository.delete(id);
    }

    @Override
    public Tag get(String name) {
        return tagRepository.get(name);

    }

    @Override
    public Tag get(int id) {
        return tagRepository.get(id);
    }
}
