package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.PostRepository;
import com.example.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private static final String MODIFY_USER_ERROR_MESSAGE = "Only admin or account holder can modify a user.";

    private final UserRepository repository;


    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public User get(String username) {
        return repository.getByUsername(username);
    }

    @Override
    public List<Comment> getUserComments(int id) {
       return repository.getUserComments(id);
    }

    @Override
    public List<Post> getUserPosts(int id) {
        return repository.getUserPosts(id);
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public void update(User user, User admin) {
        checkModifyPermissions(user.getUsername(), admin);

        boolean duplicateExists = true;
        try {
            User existingUser = repository.getByUsername(user.getUsername());
            if (existingUser.getId() == user.getId()) {
                duplicateExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }

        repository.update(user);
    }

    @Override
    public void delete(int id, User user) {

    }

    @Override
    public void delete(int postId, int commentId, User user) {

    }
    private void checkModifyPermissions(String username, User user) {
        User user1 = repository.getByUsername(username);
        if (!(user.isAdmin() || user1.getId() == user.getId())) {
            throw new AuthorizationException(MODIFY_USER_ERROR_MESSAGE);
        }
    }

}
