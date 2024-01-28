package com.example.forum.services;

import com.example.forum.models.Post;
import com.example.forum.models.User;

public interface ReactionService {

    boolean hasReacted(Post post, User user);
    boolean hasLiked(Post post, User user);
    boolean hasDisiked(Post post, User user);
}
