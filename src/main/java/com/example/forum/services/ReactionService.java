package com.example.forum.services;

import com.example.forum.models.Post;
import com.example.forum.models.User;

public interface ReactionService {

    boolean hasUpVoted(Post post, User user);
    void deleteReaction(Post post, User user);
    void clearReactions(Post post);
     void createUpVote(Post post, User user);
     void setUpVoted(Post post, User user);
    long getUpVoteCount(int postId);

}
