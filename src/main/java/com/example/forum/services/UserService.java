package com.example.forum.services;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface UserService {

    User get(String username);

    User create(User user);

    Comment create(Comment comment, User user);


    void update(User user, User admin);

    Comment update(Comment comment, User user);

    void delete (int id, User user);

    void delete (int postId, int commentId, User user);
}
