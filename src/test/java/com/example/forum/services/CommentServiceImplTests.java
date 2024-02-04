package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.CommentFilterOptions;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.repositories.CommentRepository;
import com.example.forum.repositories.PostRepository;
import com.example.forum.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.forum.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTests {

    @Mock
    PostRepository mockPostRepository;
    @Mock
    CommentRepository mockCommentRepository;
    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    CommentServiceImpl service;

    @Test
    public void create_Should_CallRepository_When_CommentWIthSameIdExists() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Comment mockComment = createMockComment();

        Mockito.when(mockCommentRepository.getById(mockComment.getCommentId()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        service.create(mockPost,mockComment, mockUser);

        // Assert
        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .create(mockComment);
    }

    @Test
    public void create_Should_Throw_When_UserIsBlocked() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Comment mockComment = createMockComment();
        mockUser.setIsBlocked(true);

        // Act, Assert
        Assertions.assertThrows(
                AuthorizationException.class,
                () -> service.create(mockPost,mockComment, mockUser));
    }

    @Test
    void update_Should_CallRepository_When_UserIsCreator() {
        // Arrange
        Comment mockComment = createMockComment();
        User mockUserCreator = mockComment.getCreatedBy();

        Mockito.when(mockCommentRepository.getById(Mockito.anyInt()))
                .thenReturn(mockComment);
        // Act
        service.update(mockComment,mockUserCreator);

        // Assert
        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .update(mockComment);
    }

    @Test
    void update_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        Comment mockComment = createMockComment();
        User mockUserAdmin = createMockAdmin();

        Mockito.when(mockCommentRepository.getById(Mockito.anyInt()))
                .thenReturn(mockComment);
        // Act
        service.update(mockComment,mockUserAdmin);

        // Assert
        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .update(mockComment);
    }

    @Test
    public void update_Should_ThrowException_When_UserIsNotCreatorOrAdmin() {
        // Arrange
        Comment mockComment = createMockComment();


        Mockito.when(mockCommentRepository.getById(mockComment.getCommentId()))
                .thenReturn(mockComment);

        // Act, Assert
        Assertions.assertThrows(
                AuthorizationException.class,
                () -> service.update(mockComment, Mockito.mock(User.class)));
    }

    @Test
    public void update_Should_ThrowException_When_CommentDoesNotExist() {
        // Arrange
        Comment mockComment = createMockComment();
        int mockId = mockComment.getCommentId()+1;


        Mockito.when(mockCommentRepository.getById(mockComment.getCommentId()))
                .thenThrow(new EntityNotFoundException("Comment", mockId));

        // Act, Assert
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> service.update(mockComment, Mockito.mock(User.class)));
    }

    @Test
    void delete_Should_CallRepository_When_UserIsCreator() {
        // Arrange
        Comment mockComment = createMockComment();
        User mockUserCreator = mockComment.getCreatedBy();

        Mockito.when(mockCommentRepository.getById(Mockito.anyInt()))
                .thenReturn(mockComment);
        // Act
        service.delete(mockComment.getCommentId(),mockUserCreator);

        // Assert
        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .delete(mockComment.getCommentId());
    }
    @Test
    void delete_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        Comment mockComment = createMockComment();
        User mockAdmin  = createMockAdmin();

        Mockito.when(mockCommentRepository.getById(Mockito.anyInt()))
                .thenReturn(mockComment);
        // Act
        service.delete(mockComment.getCommentId(),mockAdmin);

        // Assert
        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .delete(mockComment.getCommentId());
    }

    @Test
    public void delete_Should_ThrowException_When_UserIsNotCreatorOrAdmin() {
        // Arrange
        Comment mockComment = createMockComment();


        Mockito.when(mockCommentRepository.getById(mockComment.getCommentId()))
                .thenReturn(mockComment);

        // Act, Assert
        Assertions.assertThrows(
                AuthorizationException.class,
                () -> service.delete(mockComment.getCommentId(),Mockito.mock(User.class)));
    }

    @Test
    public void getById_Should_ReturnComment_When_MatchByIdExist() {
        // Arrange
        Comment mockComment = createMockComment();

        Mockito.when(mockCommentRepository.getById(Mockito.anyInt()))
                .thenReturn(mockComment);

        // Act
        Comment result = service.getById(mockComment.getCommentId());

        // Assert
        Assertions.assertEquals(mockComment, result);
    }

    @Test
    void getAll_Should_CallRepository() {
        // Arrange
        CommentFilterOptions commentFilterOptions = createMockCommentFilterOptions();
        Mockito.when(mockCommentRepository.getAll(commentFilterOptions))
                .thenReturn(null);

        // Act
        service.getAll(commentFilterOptions);

        // Assert
        Mockito.verify(mockCommentRepository, Mockito.times(1))
                .getAll(commentFilterOptions);
    }



}

