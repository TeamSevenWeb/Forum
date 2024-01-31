package com.example.forum.services;

import com.example.forum.filters.CommentFilterOptions;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface CommentService {
    Comment create(Post post , Comment comment , User user);

    void update(Comment comment, User user);

    void delete (int id, User user);

    Comment getById(int id);

    List<Comment> getAll(CommentFilterOptions commentFilterOptions);
}
