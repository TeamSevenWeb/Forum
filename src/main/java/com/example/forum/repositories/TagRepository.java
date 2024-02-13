package com.example.forum.repositories;

import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.Tag;
import com.example.forum.models.User;

public interface TagRepository {
    void create(Tag tag);

    Tag update(Tag tag);

    void delete(int id);
    Tag get(String tagName);
    Tag get(int id);
}
