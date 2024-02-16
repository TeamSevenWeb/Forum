package com.example.forum.controllers;

import com.example.forum.exceptions.AuthenticationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.exceptions.InvalidReactionException;
import com.example.forum.filters.CommentFilterOptions;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.helpers.AuthenticationHelper;
import com.example.forum.helpers.CommentMapper;
import com.example.forum.helpers.PostMapper;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.models.dtos.CommentDto;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.services.CommentService;
import com.example.forum.services.PostService;
import com.example.forum.services.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final CommentService commentService;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final AuthenticationHelper authenticationHelper;
    private final ReactionService reactionService;

    @Autowired
    public PostController(PostService service, CommentService commentService, PostMapper postMapper, CommentMapper commentMapper, AuthenticationHelper authenticationHelper, ReactionService reactionService) {
        this.service = service;
        this.commentService = commentService;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
        this.authenticationHelper = authenticationHelper;
        this.reactionService = reactionService;
    }

    @GetMapping
    public List<Post> get(@RequestHeader HttpHeaders headers,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String keyWord,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            PostsFilterOptions postsFilterOptions = new PostsFilterOptions(title, keyWord,createdBy, tagName, sortBy, sortOrder);
            return service.getAll(postsFilterOptions);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/comments/")
    public List<Comment> getComments(@RequestHeader HttpHeaders headers,
                          @RequestParam(required = false) Integer post,
                          @RequestParam(required = false) String keyWord,
                          @RequestParam(required = false) String createdBy,
                          @RequestParam(required = false) String sortBy,
                          @RequestParam(required = false) String sortOrder) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            CommentFilterOptions commentFilterOptions = new CommentFilterOptions(post, keyWord,createdBy, sortBy, sortOrder);
            return commentService.getAll(commentFilterOptions);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Post get(@PathVariable int id) {
        try {
            return service.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/most+commented")
    public List<Post> getTopTenCommented() {
        try {
            return service.getMostCommentedPosts();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/upvotes")
    public long getPostUpVotes(@PathVariable int id) {
            return reactionService.getUpVoteCount(id);
        }



    @GetMapping("/most+recent")
    public List<Post> getTenMostRecent(){
        try {
            return service.getTenMostRecentPosts();
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Post create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromDto(postDto);
            service.create(post, user);
            return post;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/{id}/comment")
    public Comment createComment(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody CommentDto commentDto) {
        try{
            User user = authenticationHelper.tryGetUser(headers);
            Post post = service.get(id);
            Comment comment = commentMapper.fromDto(commentDto);
            commentService.create(post,comment,user);
            return comment;
        }
        catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PutMapping("/upvote/{postId}")

    public void upvote(@RequestHeader HttpHeaders headers, @PathVariable int postId) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = service.get(postId);
            service.upvote(post,user);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (InvalidReactionException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "Update a post")
    @PutMapping("/{id}")
    public Post update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromDto(id, postDto);
            service.update(post, user);
            return post;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/comment/{id}")
    public Comment updateComment(@PathVariable int id, @RequestHeader HttpHeaders headers, @Valid @RequestBody CommentDto commentDto) {
       try{
           User user = authenticationHelper.tryGetUser(headers);
        Comment comment = commentMapper.fromDto(id, commentDto);
        commentService.update(comment, user);
        return comment;
       } catch (EntityNotFoundException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
       } catch (AuthenticationException e) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
       }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try{
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id,user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            commentService.delete(id,user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
