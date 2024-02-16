package com.example.forum.services;

import com.example.forum.exceptions.AuthenticationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.CommentRepository;
import com.example.forum.repositories.PostRepository;
import com.example.forum.repositories.ReactionRepository;
import com.example.forum.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.forum.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTests {

    @Mock
    PostRepository mockRepository;
    @Mock
    CommentRepository mockCommentRepository;
    @Mock
    UserRepository mockUserRepository;

    @Mock
    ReactionRepository reactionRepository;

    @Mock
    ReactionService reactionService;

    @InjectMocks
    PostServiceImpl service;

    @Test
    void getAll_Should_CallRepository() {
        // Arrange
        PostsFilterOptions mockFilterOptions = createMockPostFilterOptions();
        Mockito.when(mockRepository.getAll(mockFilterOptions))
                .thenReturn(null);

        // Act
        service.getAll(mockFilterOptions);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .getAll(mockFilterOptions);
    }

    @Test
    void getMostCommentedPosts_Should_CallRepository() {
        // Arrange
        Mockito.when(mockRepository.getTopTenCommented())
                .thenReturn(null);
        // Act
        service.getMostCommentedPosts();

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .getTopTenCommented();
    }

    @Test
    void getMostCommentedPosts_Should_ReturnPosts() {
        // Arrange
        List<Post> commentedPosts = createCommentedPosts();
        Mockito.when(mockRepository.getTopTenCommented())
                .thenReturn(commentedPosts);

        // Act
        List<Post> result = service.getMostCommentedPosts();

        // Assert
        Assertions.assertEquals(commentedPosts, result);
    }

    @Test
    public void get_Should_ReturnPost_When_MatchByIdExist() {
        // Arrange
        Post mockPost = createMockPost();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        // Act
        Post result = service.get(mockPost.getPostId());

        // Assert
        Assertions.assertEquals(mockPost, result);
    }
    @Test
    public void create_Should_CallRepository_When_PostWithSameTitleDoesNotExist() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();

        Mockito.when(mockRepository.get(mockPost.getTitle()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.create(mockPost, mockUser);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .create(mockPost);
    }

    @Test
    public void create_Should_Throw_When_PostWithSameTitleExists() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();


        Mockito.when(mockRepository.get(mockPost.getTitle()))
                .thenReturn(mockPost);

        // Act, Assert
        Assertions.assertThrows(
                EntityDuplicateException.class,
                () -> service.create(mockPost, mockUser));
    }
    @Test
    void create_Should_Throw_When_UserIsBlocked() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = mockPost.getCreatedBy();
        mockUser.setIsBlocked(true);

        // Act, Assert
        Assertions.assertThrows(
                AuthenticationException.class,
                () -> service.create(mockPost, mockUser));
    }

    @Test
    void update_Should_CallRepository_When_UserIsCreator() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUserCreator = mockPost.getCreatedBy();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Mockito.when(mockRepository.get(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.update(mockPost, mockUserCreator);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .update(mockPost);
    }

    @Test
    void update_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        User mockUserAdmin = createMockAdmin();
        Post mockPost = createMockPost();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Mockito.when(mockRepository.get(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.update(mockPost, mockUserAdmin);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .update(mockPost);
    }

    @Test
    public void update_Should_CallRepository_When_UpdatingExistingPost() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUserCreator = mockPost.getCreatedBy();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Mockito.when(mockRepository.get(Mockito.anyString()))
                .thenReturn(mockPost);

        // Act
        service.update(mockPost, mockUserCreator);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .update(mockPost);
    }

    @Test
    public void update_Should_ThrowException_When_UserIsNotCreatorOrAdmin() {
        // Arrange
        Post mockPost = createMockPost();

        Mockito.when(mockRepository.get(mockPost.getPostId()))
                .thenReturn(mockPost);

        // Act, Assert
        Assertions.assertThrows(
                AuthenticationException.class,
                () -> service.update(mockPost, Mockito.mock(User.class)));
    }

    @Test
    public void update_Should_ThrowException_When_PostWithSameTitleExists() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUserCreator = mockPost.getCreatedBy();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        Post mockExistingPostWithSameTitle = createMockPost();
        mockExistingPostWithSameTitle.setPostId(2);

        Mockito.when(mockRepository.get(mockPost.getTitle()))
                .thenReturn(mockExistingPostWithSameTitle);

        // Act, Assert
        Assertions.assertThrows(
                EntityDuplicateException.class,
                () -> service.update(mockPost, mockUserCreator));
    }

    @Test
    void delete_Should_CallRepository_When_UserIsCreator() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUserCreator = mockPost.getCreatedBy();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        // Act
        service.delete(1, mockUserCreator);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .delete(1);
    }

    @Test
    void delete_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        User mockUserAdmin = createMockAdmin();
        Post mockPost = createMockPost();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);
        // Act
        service.delete(1, mockUserAdmin);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1))
                .delete(1);
    }

    @Test
    void delete_Should_ThrowException_When_UserIsNotAdminOrCreator() {
        // Arrange
        Post mockPost = createMockPost();

        Mockito.when(mockRepository.get(Mockito.anyInt()))
                .thenReturn(mockPost);

        User mockUser = Mockito.mock(User.class);

        // Act, Assert
        Assertions.assertThrows(
                AuthenticationException.class,
                () -> service.delete(1, mockUser));
    }

    @Test
    void upvote_Should_Call_reactionService_if_Valid_Arguments(){
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();

        // Act
        Mockito.when(reactionService.hasUpVoted(mockPost,mockUser)).thenThrow(EntityNotFoundException.class);
        service.upvote(mockPost,mockUser);

        // Assert
        Mockito.verify(reactionService, Mockito.times(1)).createUpVote(mockPost,mockUser);

    }


}
