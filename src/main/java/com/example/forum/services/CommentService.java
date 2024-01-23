package com.example.forum.services;

import com.example.forum.models.Comment;

public interface CommentService {
    Comment create(Comment comment);

    Comment update(Comment comment);

    void delete (Comment comment);
}
