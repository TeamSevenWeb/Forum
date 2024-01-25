package com.example.forum.services;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment create(Post post, Comment comment, User user) {
        comment.setCreatedBy(user);
        comment.setPost(post);
        repository.create(comment);
        return comment;
    }

    @Override
    public void update(Comment comment) {
        repository.update(comment);


    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public Comment getById(int id) {
        return repository.getById(id);
    }
}
