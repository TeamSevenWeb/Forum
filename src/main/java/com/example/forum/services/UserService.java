package com.example.forum.services;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface UserService {

    User get(String username);

    User get(int id);

    List<Comment> getUserComments(int id);

    List<Post> getUserPosts(int id);

    User create(User user);

    void update(User user, User admin);

    void delete (int id, User user);

    void delete (int postId, int commentId, User user);

    User block(User user, int id);

    User unblock(User user,int id);
}
