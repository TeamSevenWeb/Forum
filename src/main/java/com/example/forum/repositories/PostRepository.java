package com.example.forum.repositories;

import com.example.forum.models.Post;

import java.util.List;

public interface PostRepository {

    void create(Post post);

    void update(Post post);

    void delete (Post post);

    List<Post> getAll();

    Post get(String title);
    Post get(int id);
}
