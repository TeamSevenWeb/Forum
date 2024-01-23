package com.example.forum.services;

import com.example.forum.models.Post;

import java.util.List;

public interface PostService {

    Post create(Post post);

    Post update(Post post);

    void delete (Post post);

    List<Post> getAll();

    Post getById(int id);

    Post getByTitle(String title);

    Post getByKeyword(String keyword);

}
