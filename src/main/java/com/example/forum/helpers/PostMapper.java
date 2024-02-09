package com.example.forum.helpers;

import com.example.forum.models.Post;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    private final PostService postService;

    @Autowired
    public PostMapper(PostService postService){
        this.postService = postService;
    }

    public Post fromDto(int id, PostDto postDto){
        Post post = fromDto(postDto);
        post.setPostId(id);
        Post repositoryPost = postService.get(id);
        post.setCreatedBy(repositoryPost.getCreatedBy());
        return post;
    }
    public Post fromDto(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDateAndTimeOfCreation(LocalDateTime.now());
        return post;
    }

    public PostDto toDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        return postDto;
    }
}
