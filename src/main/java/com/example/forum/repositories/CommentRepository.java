package com.example.forum.repositories;

import com.example.forum.models.Comment;

public interface CommentRepository {

    Comment create(Comment comment);

    Comment update(Comment comment);

    void delete (Comment comment);



}
