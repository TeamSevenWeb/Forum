package com.example.forum.repositories;

import com.example.forum.models.User;

public interface UserRepository {
    User create(User user);

    User update(User user);

    void delete (User user);

    User getByUsername(String username);

    User getByEmail(String email);

    User getByFirstName(String firstName);

    void setAdmin();

    void setBlocked();

    void setUnblocked();

}
