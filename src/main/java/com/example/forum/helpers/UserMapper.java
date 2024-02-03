package com.example.forum.helpers;

import com.example.forum.models.User;
import com.example.forum.models.dtos.UserDto;
import com.example.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class UserMapper {

    private final UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User fromDto(int id, UserDto userDto){
        User user = fromDto(userDto);
        user.setId(id);
        User repositoryUser = userService.get(id);
        user.setUserComments(repositoryUser.getUserComments());
        user.setUserPosts(repositoryUser.getUserPosts());
        if (repositoryUser.isAdmin()){
            user.setIsAdmin(true);
        }
        if (repositoryUser.isBlocked()){
            user.setIsBlocked(true);
        }
        return user;
    }

    public User fromDto(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setDateOfRegistration(LocalDateTime.now());
        return user;
    }
}
