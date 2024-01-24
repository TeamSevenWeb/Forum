package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private static final String MODIFY_POST_ERROR_MESSAGE = "Only admin or post creator can modify a post.";
    private static final String CREATE_POST_ERROR_MESSAGE = "Only admin or not blocked users can create a post.";


    private final PostRepository repository;
    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post get(int id) {
        return repository.get(id);
    }

    @Override
    public void create(Post post, User user) {
        if(user.isBlocked()){
            throw new AuthorizationException(CREATE_POST_ERROR_MESSAGE);
        };
        boolean duplicateExists = true;
        try {
            repository.get(post.getTitle());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Post", "title", post.getTitle());
        }

        post.setCreatedBy(user);
        repository.create(post);
    }

    @Override
    public void update(Post post, User user) {
        checkModifyPermissions(post.getPostId(), user);

        boolean duplicateExists = true;
        try {
            Post existingPost = repository.get(post.getTitle());
            if (existingPost.getPostId() == post.getPostId()) {
                duplicateExists = false;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Post", "id", post.getTitle());
        }

        repository.update(post);
    }

    @Override
    public void delete(Post post, User user) {

    }

    private void checkModifyPermissions(int id, User user) {
        Post post = repository.get(id);
        if (!(user.isAdmin() || post.getCreatedBy().equals(user))) {
            throw new AuthorizationException(MODIFY_POST_ERROR_MESSAGE);
        }
    }
}
