package com.example.forum.services;

import com.example.forum.filters.UserFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface UserService {

    User get(int id);

    User getByUsername(String username);

    User getByEmail(String email);

    List<User> get(UserFilterOptions filterOptions, User user);

    List<Comment> getUserComments(int id);

    List<Post> getUserPosts(int id);

    void create(User user);

    void update(User user, User admin);

   void delete (int id, User user);

    void block(User user, int id);

    void unblock(User user,int id);
}
