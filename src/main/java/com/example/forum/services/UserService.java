package com.example.forum.services;

import com.example.forum.filters.UserFilterOptions;
import com.example.forum.filters.UserPostsFilterOptions;
import com.example.forum.filters.dtos.UserFilterDto;
import com.example.forum.filters.dtos.UserPostsFilterDto;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface UserService {

    User get(int id);

    User getByUsername(String username);

    User getByEmail(String email);

    List<User> getAll(UserFilterOptions filterOptions, User user);

    List<Comment> getUserComments(UserPostsFilterDto filterDto, User user);

    List<Post> getUserPosts(UserPostsFilterDto filterDto, User user);

    void create(User user);

    void update(User user, User admin);

   void delete (int id, User user);

    void block(int id, User user);

    void unblock(int id, User user);

    void makeAdmin(int id, User user);
}
