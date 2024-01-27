package com.example.forum.repositories;

import com.example.forum.models.Comment;

import java.util.List;

public interface CommentRepository {

    void create(Comment comment);

    Comment update(Comment comment);

    void delete (int id);

     List<Comment> getAll();

     Comment getById(int id);
}
