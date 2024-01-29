package com.example.forum.controllers;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.exceptions.UserDontHaveAnyException;
import com.example.forum.helpers.AuthenticationHelper;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final AuthenticationHelper authenticationHelper;
    @Autowired
    public UserController(UserService service, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public User get(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName) {
//        FilterOptions filterOptions = new FilterOptions(userId, userName, email, firstName, sortBy, sortOrder);
//        return service.get(filterOptions);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}/posts")
    public List<Post> getUserPosts(@RequestHeader HttpHeaders headers,@PathVariable int id){
       try {
        return service.getUserPosts(id);
       } catch (UserDontHaveAnyException e){
           throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
       }
    };
    @GetMapping("/{id}/comments")
    public List<Comment> getUserComments(@RequestHeader HttpHeaders headers,@PathVariable int id){
        try {
            return service.getUserComments(id);
        } catch (UserDontHaveAnyException e){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    };
    @PostMapping
    public User create(@RequestHeader HttpHeaders headers, @Valid @RequestBody User user) {
        try {
            User user1 = authenticationHelper.tryGetUser(headers);
            User user2 = service.get(user.getUsername());
            service.update(user2, user1);
            service.create(user2);
            return user2;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody User user) {
        try {
            User user1 = authenticationHelper.tryGetUser(headers);
            User user2 = service.get(user.getUsername());
            service.update(user2, user1);
            return user2;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/block/{id}")
    public User block(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.block(user,id);
        }  catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/unblock/{id}")
    public User unblock(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.unblock(user,id);
        }  catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id,user);
        }  catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
