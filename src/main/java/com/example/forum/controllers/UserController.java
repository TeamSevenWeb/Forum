package com.example.forum.controllers;

import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.models.dtos.CommentDto;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.models.dtos.UserDto;
import com.example.forum.services.PostService;
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
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;
    @Autowired
    public UserController(UserService service, UserMapper userMapper, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public User get(
            @RequestParam(required = false) int userId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName) {
//        FilterOptions filterOptions = new FilterOptions(name, minAbv, maxAbv, styleId, sortBy, sortOrder);
//        return service.get(filterOptions);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public User create(@RequestHeader HttpHeaders headers, @Valid @RequestBody UserDto userDto) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{postId}/comment")
    public Comment create(@RequestHeader HttpHeaders headers,@PathVariable int postId, @Valid @RequestBody CommentDto commentDto) {
        //commentService.create(Post, Comment, User)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody UserDto userDto) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{postId}/comment/{id}")
    public Comment update(@RequestHeader HttpHeaders headers,@PathVariable int postId, @Valid @RequestBody CommentDto commentDto) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping ("/{postId}/comment/{commentId}")
    public void delete(@RequestHeader HttpHeaders headers,@PathVariable int postId, @PathVariable int commentId) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
