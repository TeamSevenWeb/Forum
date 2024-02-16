package com.example.forum.services;

import com.example.forum.exceptions.AuthenticationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.*;
import com.example.forum.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private static final String MODIFY_POST_ERROR_MESSAGE = "Only the creator of this post can modify it.";
    private static final String CREATE_POST_ERROR_MESSAGE = "Only active users can create a post.";


    private final PostRepository repository;

    private final UserRepository userRepository;
    private final ReactionService reactionService;
    private final TagService tagService;
    @Autowired
    public PostServiceImpl(PostRepository repository, UserRepository userRepository, ReactionService reactionService, ReactionRepository reactionRepository, CommentRepository commentRepository, TagRepository tagRepository, TagService tagService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.reactionService = reactionService;
        this.tagService = tagService;
    }

    @Override
    public List<Post> getAll(PostsFilterOptions postsFilterOptions) {
        return repository.getAll(postsFilterOptions);
    }

    @Override
    public List<Post> getMostCommentedPosts() {
        return repository.getTopTenCommented();
    }

    @Override
    public List<Post> getTenMostRecentPosts(){
        return repository.getTenMostRecent();
    }
    @Override
    public Post get(int id) {
        return repository.get(id);
    }

    @Override
    public void create(Post post, User user) {
        if(user.isBlocked()){
            throw new AuthenticationException(CREATE_POST_ERROR_MESSAGE);
        }
        if (user.getUserPosts().stream().anyMatch(p -> p.getPostId() == post.getPostId())) {
            return;
        }
        boolean duplicateExists = true;
        try {
            repository.get(post.getTitle());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists || user.getUserPosts().stream().anyMatch(p -> p.getPostId() == post.getPostId())) {
            throw new EntityDuplicateException("Post", "title", post.getTitle());
        }

        post.setCreatedBy(user);
        repository.create(post);
        user.getUserPosts().add(post);
        userRepository.update(user);
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
            throw new EntityDuplicateException("Post", "title", post.getTitle());
        }

        repository.update(post);
    }

    @Override
    public void delete(int id, User user) {
        checkModifyPermissions(id, user);
        if (!user.isAdmin()) {
            user.getUserPosts().stream()
                    .filter(p -> p.getPostId() == id)
                    .findAny().orElseThrow( () -> new EntityNotFoundException("Post", id));
        }
        Post post = repository.get(id);
        user.getUserPosts().remove(post);
        userRepository.update(user);
        repository.delete(id);
    }

    @Override
    public void upvote(Post post, User user) {

        try {
            if (reactionService.hasUpVoted(post,user)){
                reactionService.deleteReaction(post,user);
            }
            else if (!reactionService.hasUpVoted(post,user)){
                reactionService.setUpVoted(post,user);
            }
        } catch (EntityNotFoundException e) {
           reactionService.createUpVote(post,user);
        }
    }

    @Override
    public void addTag(Post post, Tag tag) {
        //TODO optimize method
        if (post.getPostTags().contains(tag)){
            return;
        }
        Tag existingTag;
        try {
            existingTag = tagService.get(tag.getName());
        } catch (EntityNotFoundException e) {
            existingTag = tagService.create(tag);
        }
        if (existingTag == null) {
            existingTag = tagService.create(tag);
        }
        try {
            tagService.create(existingTag);
            post.getPostTags().add(existingTag);
            repository.update(post);
        }
        catch (EntityDuplicateException e){
            post.getPostTags().add(existingTag);
            repository.update(post);}

    }


    private void checkModifyPermissions(int id, User user) {
        Post post = repository.get(id);
        if (!post.getCreatedBy().equals(user)&&!user.isAdmin()) {
            throw new AuthenticationException(MODIFY_POST_ERROR_MESSAGE);
        }
    }
}
