package com.example.forum.repositories;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface UserRepository {


    User get(int id);

    User getByUsername(String username);

    User getByEmail(String email);

    User getByFirstName(String firstName);

    List<Comment> getUserComments(User user);

    List<Post> getUserPosts(User user);

    User create(User user);

    User update(User user);

    void delete (User user);

    void setAdmin();

    User setBlocked(int id);

    User setUnblocked(int id);

}
