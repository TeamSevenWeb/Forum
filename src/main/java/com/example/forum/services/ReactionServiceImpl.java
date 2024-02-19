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
    public boolean hasUpVoted(Post post, User user) {
        try {
            Reaction reaction = reactionRepository.get(post,user);
            if (reaction.getIsUpVoted()){
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
    public void clearReactions(Post post) {
        reactionRepository.clearAll(post);
    }

    @Override
    public void createUpVote(Post post, User user) {
        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setCreatedBy(user);
        reaction.setIsUpVoted(true);
        reactionRepository.create(reaction);
    }


    @Override
    public void setUpVoted(Post post, User user) {
        Reaction reaction = reactionRepository.get(post,user);
        reaction.setIsUpVoted(true);
        reactionRepository.update(reaction);
    }

    @Override
    public long getUpVoteCount(int postId) {
        return reactionRepository.getUpVotedPostCount(postRepository.get(postId));
    }

}
