package com.example.forum.repositories;

import com.example.forum.filters.CommentFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;

import java.util.List;

public interface CommentRepository {

    void create(Comment comment);

    Comment update(Comment comment);

    void delete (int id);

     List<Comment> getAll(CommentFilterOptions commentFilterOptions);

     Comment getById(int id);
}
