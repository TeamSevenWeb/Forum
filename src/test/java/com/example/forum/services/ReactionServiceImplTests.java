package com.example.forum.services;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;
import com.example.forum.repositories.CommentRepository;
import com.example.forum.repositories.PostRepository;
import com.example.forum.repositories.ReactionRepository;
import com.example.forum.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.forum.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class ReactionServiceImplTests {
    @Mock
    PostRepository mockPostRepository;
    @Mock
    ReactionRepository mockReactionRepository;
    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    ReactionServiceImpl service;

    @Test
    public void createLike_Should_CallRepository_When_ValidArguments() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();

        // Act
        service.createLike(mockPost, mockUser);

        // Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1))
                .create(Mockito.any(Reaction.class));
    }

    @Test
    public void createDislike_Should_CallRepository_When_ValidArguments() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();

        // Act
        service.createLike(mockPost, mockUser);

        // Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1))
                .create(Mockito.any(Reaction.class));
    }

    @Test
    public void createLike_Should_Create_Reaction_With_True_isLiked_When_ValidArguments() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        ArgumentCaptor<Reaction> reactionCaptor = ArgumentCaptor.forClass(Reaction.class);
        // Act
        service.createLike(mockPost, mockUser);

        // Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1)).create(reactionCaptor.capture());
        Reaction capturedReaction = reactionCaptor.getValue();
        Assertions.assertTrue(capturedReaction.getIsLiked());
    }

    @Test
    public void createDislike_Should_Create_Reaction_With_False_isLiked_When_ValidArguments() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        ArgumentCaptor<Reaction> reactionCaptor = ArgumentCaptor.forClass(Reaction.class);
        // Act
        service.createDislike(mockPost, mockUser);

        // Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1)).create(reactionCaptor.capture());
        Reaction capturedReaction = reactionCaptor.getValue();
        Assertions.assertFalse(capturedReaction.getIsLiked());
    }

    @Test
    public void hasLiked_Should_Call_Repository_If_ValidArguments(){
        //Arrange, Act
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockLike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        service.hasLiked(mockPost,mockUser);

        //Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1))
                .get(mockPost,mockUser);
    }
    @Test
    public void hasLiked_Should_Return_True_If_Liked(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockLike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);

        //Assert, Act
        Assertions.assertTrue(service.hasLiked(mockPost,mockUser));
    }

    @Test
    public void hasLiked_Should_Return_False_If_Disliked(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDislike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);

        //Assert, Act
        Assertions.assertFalse(service.hasLiked(mockPost,mockUser));
    }

    @Test
    public void hasReacted_Should_Return_True_If_Reacted(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDislike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);

        //Assert, Act
        Assertions.assertTrue(service.hasReacted(mockPost,mockUser));
    }

    @Test
    public void hasReacted_Should_Return_False_If_Not_Reacted(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenThrow(new EntityNotFoundException("Test"));

        //Assert, Act
        Assertions.assertFalse(service.hasReacted(mockPost,mockUser));
    }
    @Test
    public void deleteReaction_Should_Call_Repository_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDislike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.deleteReaction(mockPost,mockUser);
        //Assert
        Mockito.verify(mockReactionRepository,Mockito.times(1))
                .delete(mockReaction.getReactionId() );
    }

    @Test
    public void setLiked_Should_Call_Repository_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDislike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.setLiked(mockPost,mockUser);
        //Assert
        Mockito.verify(mockReactionRepository,Mockito.times(1))
                .update(mockReaction);
    }
    @Test
    public void setDisliked_Should_Call_Repository_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockLike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.setDisliked(mockPost,mockUser);
        //Assert
        Mockito.verify(mockReactionRepository,Mockito.times(1))
                .update(mockReaction);
    }
    @Test
    public void setLiked_Should_Set_isLiked_To_True_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDislike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.setLiked(mockPost,mockUser);
        //Assert
        Assertions.assertTrue(mockReaction.getIsLiked());
    }

    @Test
    public void setDisliked_Should_Set_isLiked_To_False_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockLike();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.setDisliked(mockPost,mockUser);
        //Assert
        Assertions.assertFalse(mockReaction.getIsLiked());
    }



}
