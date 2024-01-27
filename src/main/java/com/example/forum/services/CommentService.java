package com.example.forum.services;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

public interface CommentService {
    Comment create(Post post , Comment comment , User user);

    void update(Comment comment, User user);

    void delete (int id, User user);

    Comment getById(int id);
}
