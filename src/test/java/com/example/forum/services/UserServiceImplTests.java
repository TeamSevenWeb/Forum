package com.example.forum.services;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.UserFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
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
public class UserServiceImplTests {

    @InjectMocks
    UserServiceImpl mockUserService;
    @Mock
    UserRepository mockUserRepository;
    @Test
    void getAll_Should_CallRepository() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setIsAdmin(true);
        UserFilterOptions mockFilterOptions = createMockUserFilterOptions();
        Mockito.when(mockUserRepository.getAll(mockFilterOptions))
                .thenReturn(null);

        // Act
        mockUserService.getAll(mockFilterOptions,mockUser);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .getAll(mockFilterOptions);
    }

    @Test
    void  getAll_Should_Throw_When_User_Is_Not_An_Admin(){
        //Arrange
        User mockUser = createMockUser();
        mockUser.setIsAdmin(false);
        UserFilterOptions mockFilterOptions = createMockUserFilterOptions();

        //Act,Assert
        Assertions.assertThrows(AuthorizationException.class,
                ()-> mockUserService.getAll(mockFilterOptions,mockUser));

    }

    @Test
    void get_Should_CallRepository(){
        //Arrange
        Mockito.when(mockUserRepository.get(1))
                .thenReturn(null);
        //Act
        mockUserService.get(1);

        //Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .get(1);
    }

    @Test
    void get_Should_ReturnUser_When_MatchByIdExist(){
        //Arrange
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        //Act
        User result = mockUserService.get(mockUser.getId());

        //Assert
        Assertions.assertEquals(mockUser,result);
    }

    @Test
    void getByUsername_Should_CallRepository(){
        //Arrange
        Mockito.when(mockUserRepository.getByUsername(Mockito.anyString()))
                .thenReturn(null);
        //Act
        mockUserService.getByUsername(Mockito.anyString());

        //Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .getByUsername(Mockito.anyString());
    }

    @Test
    void getByUsername_Should_ReturnUser_When_MatchByEmailExist(){
        //Arrange
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.getByUsername(Mockito.anyString()))
                .thenReturn(mockUser);

        //Act
        User result = mockUserService.getByUsername(mockUser.getUsername());

        //Assert
        Assertions.assertEquals(mockUser,result);
    }

    @Test
    void getByEmail_Should_CallRepository(){
        //Arrange
        Mockito.when(mockUserRepository.getByEmail(Mockito.anyString()))
                .thenReturn(null);
        //Act
        mockUserService.getByEmail(Mockito.anyString());

        //Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .getByEmail(Mockito.anyString());
    }
    @Test
    void getByEmail_Should_ReturnUser_When_MatchByEmailExist(){
        //Arrange
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.getByEmail(Mockito.anyString()))
                .thenReturn(mockUser);

        //Act
        User result = mockUserService.getByEmail(mockUser.getUsername());

        //Assert
        Assertions.assertEquals(mockUser,result);
    }

    @Test
    void getUserPosts_Should_ReturnUserPosts_When_ThereIsAny(){
        //Arrange
        User mockUser = createMockUser();
        mockUser.getUserPosts().add(createMockPost());

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);
        Mockito.when(mockUserRepository.getUserPosts(mockUser))
                .thenReturn(mockUser.getUserPosts().stream().toList());

        //Act
       List<Post> result = mockUserService.getUserPosts(Mockito.anyInt());

       //Assert
        Assertions.assertEquals(mockUser.getUserPosts().stream().toList(),result);
    }

//    @Test
//    void getUserPosts_Should_Throw_When_ThereIsNotAny(){
//        //Arrange
//        User mockUser = createMockUser();
//
//        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
//                .thenReturn(mockUser);
//
//        //Act, Assert
//        Assertions.assertThrows(EntityNotFoundException.class,
//                ()-> mockUserService.getUserPosts(Mockito.anyInt()));
//    }

    @Test
    void getUserComments_Should_ReturnUserComments_When_ThereIsAny(){
        //Arrange
        User mockUser = createMockUser();
        mockUser.getUserComments().add(createMockComment());

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);
        Mockito.when(mockUserRepository.getUserComments(mockUser))
                .thenReturn(mockUser.getUserComments().stream().toList());

        //Act
        List<Comment> result = mockUserService.getUserComments(Mockito.anyInt());

        //Assert
        Assertions.assertEquals(mockUser.getUserComments().stream().toList(),result);
    }

    @Test
    public void create_Should_CallRepository_When_UserWithSameUsernameAndEmailDoesNotExist() {
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.getByUsername(mockUser.getUsername()))
                .thenThrow(EntityNotFoundException.class);

        Mockito.when(mockUserRepository.getByEmail(mockUser.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        mockUserService.create(mockUser);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .create(mockUser);
    }

    @Test
    public void create_Should_Throw_When_UserWithSameUsernameExists() {
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.getByUsername(mockUser.getUsername()))
                .thenReturn(mockUser);

        // Act, Assert
        Assertions.assertThrows(
                EntityDuplicateException.class,
                () -> mockUserService.create(mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserWithSameEmailExists() {
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.getByUsername(Mockito.anyString()))
                        .thenThrow(EntityNotFoundException.class);

        Mockito.when(mockUserRepository.getByEmail(mockUser.getEmail()))
                .thenReturn(mockUser);

        // Act, Assert
        Assertions.assertThrows(
                EntityDuplicateException.class,
                () -> mockUserService.create(mockUser));
    }

    @Test
    void update_Should_CallRepository_When_UserIsSame() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setId(1);
        User mockUserToBeUpdated = createMockUser();
        mockUserToBeUpdated.setId(1);

        Mockito.when(mockUserRepository.getByEmail(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        mockUserService.update(mockUserToBeUpdated,mockUser);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }
    @Test
    void update_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setId(1);
        mockUser.setIsAdmin(true);
        User mockUserToBeUpdated = createMockUser();
        mockUserToBeUpdated.setId(2);

        Mockito.when(mockUserRepository.getByEmail(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        mockUserService.update(mockUserToBeUpdated,mockUser);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }

    @Test
    void update_Should_Throw_When_UserIsNotSameOrAdmin() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setId(1);
        User mockUserToBeUpdated = createMockUser();
        mockUserToBeUpdated.setId(2);

        // Act,Assert
       Assertions.assertThrows(AuthorizationException.class,
               ()-> mockUserService.update(mockUserToBeUpdated,mockUser));
    }

    @Test
    void update_Should_Throw_When_NewEmailAlreadyExist() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setId(2);
        User mockUserToBeUpdated = createMockUser();
        mockUserToBeUpdated.setId(2);
        User mockUserExistingEmail = createMockUser();

        // Act
        Mockito.when(mockUserRepository.getByEmail(Mockito.anyString()))
                .thenReturn(mockUserExistingEmail);

        //Assert
        Assertions.assertThrows(EntityDuplicateException.class,
                ()-> mockUserService.update(mockUserToBeUpdated,mockUser));
    }

    @Test
    void delete_Should_CallRepository_When_UserIsSame() {
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        // Act
        mockUserService.delete(Mockito.anyInt(),mockUser);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }

    @Test
    void delete_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        User mockAdmin = createMockUser();
        mockAdmin.setId(2);
        mockAdmin.setIsAdmin(true);
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        // Act
        mockUserService.delete(Mockito.anyInt(),mockUser);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .update(mockUser);
    }

    @Test
    void delete_Should_Throw_When_UserIsNotSameOrAdmin() {
        // Arrange
        User mockUser = createMockUser();
        mockUser.setId(2);
        User mockUserToBeDeleted = createMockUser();

        //Act
        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUserToBeDeleted);

        //Assert
        Assertions.assertThrows(AuthorizationException.class,
                ()-> mockUserService.delete(Mockito.anyInt(),mockUser));
    }

    @Test
    void block_Should_CallRepository_When_UserIsAdmin(){
        //Arrange
        User mockAdmin = createMockAdmin();
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        //Act
        mockUserService.block(Mockito.anyInt(),mockAdmin);

        //Assert
        Mockito.verify(mockUserRepository,Mockito.times(1))
                .update(mockUser);

    }
    @Test
    void block_Should_Throw_When_UserIsNotAdmin(){
        //Arrange
        User mockUser = createMockUser();
        User mockUserToBeDeleted = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUserToBeDeleted);

        //Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                ()-> mockUserService.block(Mockito.anyInt(),mockUser));
    }

    @Test
    void unblock_Should_CallRepository_When_UserIsAdmin(){
        //Arrange
        User mockAdmin = createMockAdmin();
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        //Act
        mockUserService.unblock(Mockito.anyInt(),mockAdmin);

        //Assert
        Mockito.verify(mockUserRepository,Mockito.times(1))
                .update(mockUser);

    }
    @Test
    void unblock_Should_Throw_When_UserIsNotAdmin(){
        //Arrange
        User mockUser = createMockUser();
        User mockUserToBeDeleted = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUserToBeDeleted);

        //Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                ()-> mockUserService.unblock(Mockito.anyInt(),mockUser));
    }

    @Test
    void makeAdmin_Should_CallRepository_When_UserIsAdmin(){
        //Arrange
        User mockAdmin = createMockAdmin();
        User mockUser = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUser);

        //Act
        mockUserService.makeAdmin(Mockito.anyInt(),mockAdmin);

        //Assert
        Mockito.verify(mockUserRepository,Mockito.times(1))
                .update(mockUser);

    }
    @Test
    void makeAdmin_Should_Throw_When_UserIsNotAdmin(){
        //Arrange
        User mockUser = createMockUser();
        User mockUserToBeDeleted = createMockUser();

        Mockito.when(mockUserRepository.get(Mockito.anyInt()))
                .thenReturn(mockUserToBeDeleted);

        //Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                ()-> mockUserService.makeAdmin(Mockito.anyInt(),mockUser));
    }
}
