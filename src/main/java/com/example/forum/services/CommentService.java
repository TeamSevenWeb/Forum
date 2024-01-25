package com.example.forum.services;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

public interface CommentService {
    Comment create(Post post , Comment comment , User user);

    void update(Comment comment);

    void delete (int id);

    Comment getById(int id);
}
