package com.example.forum.repositories;

import com.example.forum.filters.UserFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface UserRepository {


    User get(int id);

    User getByUsername(String username);

    User getByEmail(String email);

    List<User> getAll(UserFilterOptions filterOptions);

    List<Comment> getUserComments(User user);

    List<Post> getUserPosts(User user);

    void create(User user);

    void update(User user);
}
