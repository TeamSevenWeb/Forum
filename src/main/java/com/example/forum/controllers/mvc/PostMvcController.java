package com.example.forum.controllers.mvc;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.CommentFilterOptions;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.filters.dtos.PostFilterDto;
import com.example.forum.helpers.PostMapper;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.services.CommentService;
import com.example.forum.services.PostService;
import com.example.forum.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostMvcController {

    private final PostService service;

    private final UserService userService;

    private final CommentService commentService;

    private final PostMapper mapper;

    @Autowired
    public PostMvcController(PostService service, UserService userService, CommentService commentService, PostMapper mapper) {
        this.service = service;
        this.userService = userService;
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping
    public String showAllPosts(@ModelAttribute("postFilterOptions") PostFilterDto filterDto, Model model, HttpSession session) {
        PostsFilterOptions filterOptions = new PostsFilterOptions(
                filterDto.getTitle(),
                filterDto.getKeyword(),
                filterDto.getCreatedBy(),
                filterDto.getSortBy(),
                filterDto.getSortOrder());
        List<Post> posts = service.getAll(filterOptions);
        if (populateIsAuthenticated(session)){
            String currentUsername = (String) session.getAttribute("currentUser");
            model.addAttribute("currentUser", userService.getByUsername(currentUsername));
        }
        model.addAttribute("postFilterOptions", filterDto);
        model.addAttribute("posts", posts);
        return "PostsView";
    }

    @GetMapping("/{id}")
    public String showSinglePost(@PathVariable int id, Model model) {
        try {
            Post post = service.get(id);
            model.addAttribute("post", post);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }


    @GetMapping("/new")
    public String showNewPostPage(Model model){
        model.addAttribute("post",new PostDto());
        return "NewPostView";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto, BindingResult errors, Model model){
        if(errors.hasErrors()){
            return "NewPostView";
        }
        try {
            Post post = mapper.fromDto(postDto);
            User user = userService.get(1);
            service.create(post, user);
            return "redirect:/posts";
        }catch (EntityDuplicateException e){
            errors.rejectValue("name","post.exists",e.getMessage());
            return "NewPostView";
        }
        catch (EntityNotFoundException e){
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditPostPage(@PathVariable int id, Model model) {
        try {
            Post post = service.get(id);
            PostDto postDto = mapper.toDto(post);
            model.addAttribute("postId", id);
            model.addAttribute("post", postDto);
            return "PostUpdateView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id,@Valid @ModelAttribute("post") PostDto postDto, BindingResult errors, Model model){
        if(errors.hasErrors()){
            return "PostNew";
        }
        try {
            Post post = mapper.fromDto(id,postDto);
            User user = userService.get(1);
            service.update(post, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }catch (EntityDuplicateException e){
            errors.rejectValue("name","post.exists",e.getMessage());
            return "PostUpdateView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable int id, Model model) {
        try {
            User user = userService.get(1);
            service.delete(id, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }
}
