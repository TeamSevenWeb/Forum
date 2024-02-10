package com.example.forum.services;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;
import com.example.forum.repositories.PostRepository;
import com.example.forum.repositories.ReactionRepository;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService{

    private final ReactionRepository reactionRepository;

    private final PostRepository postRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository, PostRepository postRepository) {
        this.reactionRepository = reactionRepository;
        this.postRepository = postRepository;
    }

    @Override
    public boolean hasReacted(Post post, User user) {
        try {
            Reaction reaction = reactionRepository.get(post,user);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasLiked(Post post, User user) {
        try {
            Reaction reaction = reactionRepository.get(post,user);
            if (reaction.getIsLiked()){
                return true;
            }
        }catch (EntityNotFoundException e) {
            return false;
        }
            return false;

    }

    @Override
    public void deleteReaction(Post post, User user) {
        Reaction reaction = reactionRepository.get(post,user);
        reactionRepository.delete(reaction.getReactionId());
    }

    @Override
    public void createLike(Post post, User user) {
        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setCreatedBy(user);
        reaction.setIsLiked(true);
        reactionRepository.create(reaction);
    }

    @Override
    public void createDislike(Post post, User user) {
        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setCreatedBy(user);
        reaction.setIsLiked(false);
        reactionRepository.create(reaction);
    }

    @Override
    public void setLiked(Post post, User user) {
        Reaction reaction = reactionRepository.get(post,user);
        reaction.setIsLiked(true);
        reactionRepository.update(reaction);
    }

    @Override
    public void setDisliked(Post post, User user) {
        Reaction reaction = reactionRepository.get(post,user);
        reaction.setIsLiked(false);
        reactionRepository.update(reaction);
    }

    @Override
    public long getUpVoteCount(int postId) {
        return reactionRepository.getUpVotedPostCount(postRepository.get(postId));
    }

}
