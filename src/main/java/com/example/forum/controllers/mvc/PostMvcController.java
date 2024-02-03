package com.example.forum.controllers.mvc;

import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.helpers.PostMapper;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.services.PostService;
import com.example.forum.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostMvcController {

    private final PostService service;

    private final UserService userService;

    private final PostMapper mapper;

    @Autowired
    public PostMvcController(PostService service, UserService userService, PostMapper mapper) {
        this.service = service;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public String showAllBeers(Model model) {
        model.addAttribute("posts", service.getAll(new PostsFilterOptions()));
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
    public String createBeer(@Valid @ModelAttribute("post") PostDto postDto, BindingResult errors, Model model){
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
            model.addAttribute("error",e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditPage(@PathVariable int id, Model model){
        Post post = service.get(id);
        PostDto postDto = mapper.toDto(post);
        model.addAttribute("post",postDto);
        return "PostUpdateView";
    }

    @PostMapping("/{id}/update")
    public String updateBeer(@PathVariable int id,@Valid @ModelAttribute("post") PostDto postDto, BindingResult errors){
        if(errors.hasErrors()){
            return "PostNew";
        }
        try {
            Post post = mapper.fromDto(id,postDto);
            User user = userService.get(1);
            service.update(post, user);
            return "redirect:/posts";
        }catch (EntityDuplicateException e){
            errors.rejectValue("name","post.exists",e.getMessage());
            return "PostUpdateView";
        }
    }
}
