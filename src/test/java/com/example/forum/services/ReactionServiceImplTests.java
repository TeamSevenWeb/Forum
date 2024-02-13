package com.example.forum.services;

import com.example.forum.models.Post;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;
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
    public void createUpVote_Should_CallRepository_When_ValidArguments() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();

        // Act
        service.createUpVote(mockPost, mockUser);

        // Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1))
                .create(Mockito.any(Reaction.class));
    }


    @Test
    public void createUpVoted_Should_Create_Reaction_With_True_UpVoted_When_ValidArguments() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        ArgumentCaptor<Reaction> reactionCaptor = ArgumentCaptor.forClass(Reaction.class);
        // Act
        service.createUpVote(mockPost, mockUser);

        // Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1)).create(reactionCaptor.capture());
        Reaction capturedReaction = reactionCaptor.getValue();
        Assertions.assertTrue(capturedReaction.getIsUpVoted());
    }


    @Test
    public void hasUpVoted_Should_Call_Repository_If_ValidArguments(){
        //Arrange, Act
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockUpvote();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        service.hasUpVoted(mockPost,mockUser);

        //Assert
        Mockito.verify(mockReactionRepository, Mockito.times(1))
                .get(mockPost,mockUser);
    }
    @Test
    public void hasUpVoted_Should_Return_True_If_UpVoted(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockUpvote();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);

        //Assert, Act
        Assertions.assertTrue(service.hasUpVoted(mockPost,mockUser));
    }

    @Test
    public void hasUpVoted_Should_Return_False_If_notUpVoted(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDownVote();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);

        //Assert, Act
        Assertions.assertFalse(service.hasUpVoted(mockPost,mockUser));
    }

    @Test
    public void deleteReaction_Should_Call_Repository_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDownVote();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.deleteReaction(mockPost,mockUser);
        //Assert
        Mockito.verify(mockReactionRepository,Mockito.times(1))
                .delete(mockReaction.getReactionId() );
    }

    @Test
    public void setUpVoted_Should_Call_Repository_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDownVote();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.setUpVoted(mockPost,mockUser);
        //Assert
        Mockito.verify(mockReactionRepository,Mockito.times(1))
                .update(mockReaction);
    }

    @Test
    public void setUpVoted_Should_Set_isUpVoted_To_True_If_Valid_Arguments(){
        //Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        Reaction mockReaction = createMockDownVote();
        Mockito.when(mockReactionRepository.get(mockPost,mockUser)).thenReturn(mockReaction);
        //Act
        service.setUpVoted(mockPost,mockUser);
        //Assert
        Assertions.assertTrue(mockReaction.getIsUpVoted());
    }




}
