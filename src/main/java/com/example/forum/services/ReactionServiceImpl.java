package com.example.forum.services;

import com.example.forum.exceptions.UserDontHaveAnyException;
import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;
import com.example.forum.repositories.ReactionRepository;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService{

    private final ReactionRepository reactionRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    @Override
    public boolean hasReacted(Post post, User user) {
        try {
            Reaction reaction = reactionRepository.get(post,user);
        } catch (UserDontHaveAnyException e) {
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
            else return false;
        } catch (UserDontHaveAnyException e) {
            return false;
        }

    }

    @Override
    public boolean hasDisiked(Post post, User user) {
        Reaction reaction = reactionRepository.get(post,user);
        if (!reaction.getIsLiked()){
            return true;
        }
        else return false;
    }
}
