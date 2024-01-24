package com.example.forum.controllers;

import com.example.forum.models.Post;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;
    private final PostMapper postMapper;
    private final AuthenticationHelper authenticationHelper;
    @Autowired
    public PostController(PostService service, PostMapper postMapper, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.postMapper = postMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Post> get(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
//        FilterOptions filterOptions = new FilterOptions(title, keyWord, sortBy, sortOrder);
//        return service.get(filterOptions);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Post get(@PathVariable int id) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Post create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PostDto postDto) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public Post update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody PostDto postDto) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
