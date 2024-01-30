package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.UserFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private static final String MODIFY_USER_ERROR_MESSAGE = "Only admin or account holder can modify a user.";
    private static final String IS_NOT_ADMIN_BLOCK_ERROR_MESSAGE = "Only admin can block or unblock a user.";

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
    public List<User> get(UserFilterOptions filterOptions, User user) {
        if(!user.isAdmin()){
            throw new AuthorizationException("Only admins can view all users.");
        }
        return repository.get(filterOptions);
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Post> getUserPosts(int id) {
        User user = repository.get(id);
        return repository.getUserPosts(user);
    }

    @Override
    public List<Comment> getUserComments(int id) {
        User user = repository.get(id);
       return repository.getUserComments(user);
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
        User admin = repository.getByUsername(user.getUsername());
        User userToBeDeleted = repository.get(id);
        checkModifyPermissions(userToBeDeleted.getUsername(),admin);
        userToBeDeleted.setActive(false);
        repository.delete(userToBeDeleted);
    }

    @Override
    public User block(User user, int id) {
        User admin = repository.getByUsername(user.getUsername());
        User userToBeBlocked = repository.get(id);
        if (!admin.isAdmin()){
            throw new AuthorizationException(IS_NOT_ADMIN_BLOCK_ERROR_MESSAGE);
        }
        userToBeBlocked.setIsBlocked(true);
       return repository.setBlocked(userToBeBlocked);

    }

    @Override
    public User unblock(User user, int id) {
        User admin = repository.getByUsername(user.getUsername());
        User userToBeUnblocked = repository.get(id);
        if (!admin.isAdmin()){
            throw new AuthorizationException(IS_NOT_ADMIN_BLOCK_ERROR_MESSAGE);
        }
        repository.setUnblocked(userToBeUnblocked);
        userToBeUnblocked.setIsBlocked(false);
        return userToBeUnblocked;
    }


    private void checkModifyPermissions(String username, User user) {
        User user1 = repository.getByUsername(username);
        if (!(user.isAdmin() && user1.getId() == user.getId())) {
            throw new AuthorizationException(MODIFY_USER_ERROR_MESSAGE);
        }
    }

}
