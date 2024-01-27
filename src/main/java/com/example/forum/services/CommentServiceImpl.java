package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private static final String MODIFY_COMMENT_ERROR_MESSAGE = "Only the creator of this comment can modify it.";
    private static final String CREATE_COMMENT_ERROR_MESSAGE = "Only active users can comment.";

    private final CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment create(Post post, Comment comment, User user) {
        if(user.isBlocked()){
            throw new AuthorizationException(CREATE_COMMENT_ERROR_MESSAGE);
        };
        boolean duplicateExists = true;
        try {
            repository.getById(comment.getCommentId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Comment", "content", comment.getComment());
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
        if (!comment.getCreatedBy().equals(user)) {
            throw new AuthorizationException(MODIFY_COMMENT_ERROR_MESSAGE);
        }
    }
}
