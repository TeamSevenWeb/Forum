package com.example.forum.repositories;

import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;

import java.util.List;

public interface PostRepository {

    List<Post> getAll(PostsFilterOptions postsFilterOptions);

    Post get(String title);

    Post get(int id);

    List<Comment>getComments(int postId);

    void create(Post post);

    void update(Post post);

    void delete (int id);

    List<Post> getTopTenCommented();
}
