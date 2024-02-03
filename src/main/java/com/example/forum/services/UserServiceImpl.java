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
public class UserServiceImpl implements UserService {

    private static final String MODIFY_USER_ERROR_MESSAGE = "Only admin or account holder can modify a user.";
    private static final String IS_NOT_ADMIN_BLOCK_ERROR_MESSAGE = "Only admins can block or unblock a user.";
    public static final String ADMINS_CAN_VIEW_ALL_USERS_ERROR = "Only admins can view all users.";

    private final UserRepository repository;


    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

    @Override
    public User getByEmail(String email){
        return repository.getByEmail(email);
    }

    @Override
    public List<User> get(UserFilterOptions filterOptions, User user) {
        if(!user.isAdmin()){
            throw new AuthorizationException(ADMINS_CAN_VIEW_ALL_USERS_ERROR);
        }
        return repository.get(filterOptions);
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
    public void create(User user) {
        try {
            User existingUsername = repository.getByUsername(user.getUsername());
            throw new EntityDuplicateException("User","username",existingUsername.getUsername());
        } catch (EntityNotFoundException ignored){
        }
        try {
            User existingEmail = repository.getByEmail(user.getEmail());
            throw new EntityDuplicateException("User","email",existingEmail.getEmail());
        } catch (EntityNotFoundException ignored){
        }
      repository.create(user);
    }

    @Override
    public void update(User userToBeChanged, User user) {

        checkModifyPermissions(userToBeChanged.getUsername(), user);

        try {
            User existingEmail = repository.getByEmail(user.getEmail());
            throw new EntityDuplicateException("User","email",existingEmail.getEmail());
        } catch (EntityNotFoundException ignored){
        }

        repository.update(user);
    }

    @Override
    public void delete(int id, User user) {
        User userToBeDeleted = repository.get(id);
        checkModifyPermissions(userToBeDeleted.getUsername(), user);
        userToBeDeleted.setActive(false);
        repository.update(userToBeDeleted);
    }

    @Override
    public void block(User user, int id) {
        User userToBeBlocked = repository.get(id);
        if (!user.isAdmin()){
            throw new AuthorizationException(IS_NOT_ADMIN_BLOCK_ERROR_MESSAGE);
        }
        userToBeBlocked.setIsBlocked(true);
        repository.update(userToBeBlocked);

    }

    @Override
    public void unblock(User user, int id) {
        User userToBeUnblocked = repository.get(id);
        if (!user.isAdmin()){
            throw new AuthorizationException(IS_NOT_ADMIN_BLOCK_ERROR_MESSAGE);
        }
        userToBeUnblocked.setIsBlocked(false);
        repository.update(user);
    }

    @Override
    public void makeAdmin(User user, int id) {
        User makeAdmin = repository.get(id);
        if (!user.isAdmin()){
            throw new AuthorizationException(IS_NOT_ADMIN_BLOCK_ERROR_MESSAGE);
        }
        makeAdmin.setIsAdmin(true);
        repository.update(makeAdmin);
    }


    private void checkModifyPermissions(String username, User user) {
        User user1 = repository.getByUsername(username);
        if (!(user.isAdmin() && user1.getId() == user.getId())) {
            throw new AuthorizationException(MODIFY_USER_ERROR_MESSAGE);
        }
    }

}
