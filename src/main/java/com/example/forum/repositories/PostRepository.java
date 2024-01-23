package com.example.forum.repositories;

import com.example.forum.models.Post;

import java.util.List;

public interface PostRepository {

    Post create(Post post);

    Post update(Post post);

    void delete (Post post);

    List<Post> getAll();

    Post get(int id);
}
