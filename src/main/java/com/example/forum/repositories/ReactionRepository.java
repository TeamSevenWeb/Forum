package com.example.forum.repositories;

import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;

import java.util.List;

public interface ReactionRepository {

    void create(Reaction reaction);

    Reaction update(Reaction reaction);

    void delete(int reactionId);

    Reaction get(Post post, User user);
    Reaction get(int id);

    long getUpVotedPostCount(Post post);
}
