package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.exceptions.InvalidReactionException;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;
import com.example.forum.repositories.CommentRepository;
import com.example.forum.repositories.PostRepository;
import com.example.forum.repositories.ReactionRepository;
import com.example.forum.repositories.UserRepository;
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

    private final ReactionRepository reactionRepository;
    @Autowired
    public PostServiceImpl(PostRepository repository, UserRepository userRepository, ReactionService reactionService, ReactionRepository reactionRepository, CommentRepository commentRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.reactionService = reactionService;
        this.reactionRepository = reactionRepository;
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
            throw new AuthorizationException(CREATE_POST_ERROR_MESSAGE);
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
    public void react(String reactionType,Post post, User user) {

            if (reactionType.equals("like")){
                if (!reactionService.hasReacted(post,user)){
                    reactionService.createLike(post,user);
                    incrementLikes(post);
                }
                else if (reactionService.hasLiked(post,user) && reactionService.hasReacted(post,user)){
                    reactionService.deleteReaction(post,user);
                    decrementLikes(post);
                }
                else if (!reactionService.hasLiked(post,user) && reactionService.hasReacted(post,user)){
                    reactionService.setLiked(post,user);
                    incrementLikes(post);
                    incrementLikes(post);
                }}
        else if (reactionType.equals("dislike")){
                if (!reactionService.hasReacted(post,user)){
                    reactionService.createDislike(post,user);
                    decrementLikes(post);
                }
                else if (reactionService.hasLiked(post,user) && reactionService.hasReacted(post,user)){
                    reactionService.setDisliked(post,user);
                    decrementLikes(post);
                    decrementLikes(post);
                }
                else if (!reactionService.hasLiked(post,user) && reactionService.hasReacted(post,user)){
                    reactionService.deleteReaction(post,user);
                    incrementLikes(post);
                }}
        else throw new InvalidReactionException(reactionType);

    }

    @Override
    public void incrementLikes(Post post){
        post.setLikes(post.getLikes() + 1);
        repository.update(post);
    }
    @Override
    public void decrementLikes(Post post){
        post.setLikes(post.getLikes() - 1);
        repository.update(post);
    }

    private void checkModifyPermissions(int id, User user) {
        Post post = repository.get(id);
        if (!post.getCreatedBy().equals(user)&&!user.isAdmin()) {
            throw new AuthorizationException(MODIFY_POST_ERROR_MESSAGE);
        }
    }
}
