package com.example.forum.services;

import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.*;

import java.util.List;

public interface PostService {

    List<Post> getAll(PostsFilterOptions postsFilterOptions);

    List<Post> getMostCommentedPosts();

    List<Post> getTenMostRecentPosts();

    Post get(int id);

    void create(Post post, User user);

    void update(Post post, User user);

    void delete (int id,User user);

    void upvote(Post post, User user);

    void addTag(Post post, Tag tag);
}
