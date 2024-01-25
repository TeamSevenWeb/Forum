package com.example.forum.repositories;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;

import java.util.List;

public interface UserRepository {
    User getByUsername(String username);

    User getByEmail(String email);

    User getByFirstName(String firstName);

    List<Comment> getUserComments(int id);

    List<Post> getUserPosts(int id);

    User create(User user);

    User update(User user);

    void delete (User user);

    void setAdmin();

    void setBlocked();

    void setUnblocked();

}
