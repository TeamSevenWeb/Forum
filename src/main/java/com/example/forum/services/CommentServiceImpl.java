package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    public static final String AUTHORIZATION_ERR = "Only admins or comment creators can modify this resource.";
    public static final String USER_BLOCKED_ERR = "You are not allowed to create comments.";
    private final CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment create(Post post, Comment comment, User user) {
        if(user.isBlocked()){
            throw new AuthorizationException(USER_BLOCKED_ERR);
        }
        comment.setCreatedBy(user);
        comment.setPost(post);
        repository.create(comment);
        return comment;
    }

    @Override
    public void update(Comment comment, User user) {
        checkModifyPermissions(comment.getCommentId(), user);
        repository.update(comment);

    }

    @Override
    public void delete(int id, User user) {
        checkModifyPermissions(id,user);
        repository.delete(id);

    }

    @Override
    public Comment getById(int id) {
        return repository.getById(id);
    }

    private void checkModifyPermissions(int id, User user) {
       Comment comment = repository.getById(id);
        if (!(user.isAdmin() || comment.getCreatedBy().equals(user))) {
            throw new AuthorizationException(AUTHORIZATION_ERR);
        }
    }
}
