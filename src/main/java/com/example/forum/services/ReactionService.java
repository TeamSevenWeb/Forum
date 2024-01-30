package com.example.forum.services;

import com.example.forum.models.Post;
import com.example.forum.models.User;

public interface ReactionService {

    boolean hasReacted(Post post, User user);
    boolean hasLiked(Post post, User user);
    void deleteReaction(Post post, User user);
     void createLike(Post post, User user);
     void createDislike(Post post, User user);
     void setLiked(Post post, User user);
     void setDisliked(Post post, User user);

}
