package com.example.forum.services;

import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;

import java.util.List;

public interface PostService {

    List<Post> getAll(PostsFilterOptions postsFilterOptions);

    List<Post> getMostCommentedPosts();

    Post get(int id);

    List<Comment> getComments(int postId);

    void create(Post post, User user);

    void update(Post post, User user);

    void delete (int id,User user);

    public void react(String reactionType,Post post, User user);

    void incrementLikes(Post post);

    void decrementLikes(Post post);
}
