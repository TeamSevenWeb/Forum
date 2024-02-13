package com.example.forum.services;

import com.example.forum.models.Post;
import com.example.forum.models.Tag;
import com.example.forum.models.User;

public interface TagService {

    void create(Tag tag);

    void update(Tag tag);

    void delete (int id);

    Tag get(String name);

    Tag get(int id);

}
