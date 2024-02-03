package com.example.forum;

import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.models.dtos.PostDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Helpers {
    public static User createMockAdmin() {
        User mockUser = createMockUser();
        mockUser.setIsAdmin(true);
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setEmail("mock@user.com");
        mockUser.setDateOfRegistration(LocalDateTime.now());
        mockUser.setActive(true);
        Set<Post> mockPostsSet = new HashSet<>();
        Set<Comment> mockCommentsSet = new HashSet<>();
        mockUser.setUserPosts(mockPostsSet);
        mockUser.setUserComments(mockCommentsSet);
        return mockUser;
    }

    public static Post createMockPost() {
        var mockPost = new Post();
        mockPost.setPostId(1);
        mockPost.setTitle("TestTitleForTestPostTest");
        mockPost.setContent("TestContentForPostTestTestTest");
        User user = createMockUser();
        mockPost.setCreatedBy(user);
        mockPost.setDateAndTimeOfCreation(LocalDateTime.now());
        user.getUserPosts().add(mockPost);
        return mockPost;
    }

    public static Comment createMockComment() {
        var mockComment = new Comment();
        mockComment.setCommentId(1);
        mockComment.setComment("TestComment");
        Post post = createMockPost();
        mockComment.setPost(post);
        User user = createMockUser();
        mockComment.setCreatedBy(createMockUser());
        user.getUserComments().add(mockComment);
        return mockComment;
    }

    public static PostsFilterOptions createMockPostFilterOptions() {
        return new PostsFilterOptions(
                "TestTitleForTestPostTest",
                "TestContent",
                "MockUsername",
                "title",
                "order");
    }

    public static PostDto createPostDto() {
        PostDto dto = new PostDto();
        dto.setTitle("TestTitleForTestPostTest");
        dto.setContent("TestContentForPostTestTestTest");
        return dto;
    }

    /**
     * Accepts an object and returns the stringified object.
     * Useful when you need to pass a body to a HTTP request.
     */
    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
