package com.example.forum.controllers.mvc;

import com.example.forum.exceptions.*;
import com.example.forum.filters.PostsFilterOptions;
import com.example.forum.filters.dtos.PostFilterDto;
import com.example.forum.helpers.AuthenticationHelper;
import com.example.forum.helpers.CommentMapper;
import com.example.forum.helpers.PostMapper;
import com.example.forum.helpers.TagMapper;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.Tag;
import com.example.forum.models.User;
import com.example.forum.models.dtos.CommentDto;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.models.dtos.TagDto;
import com.example.forum.services.CommentService;
import com.example.forum.services.PostService;
import com.example.forum.services.ReactionService;
import com.example.forum.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostMvcController {

    private final PostService service;

    private final UserService userService;

    private final CommentService commentService;

    private final ReactionService reactionService;

    private final PostMapper mapper;

    private final CommentMapper commentMapper;

    private final TagMapper tagMapper;

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public PostMvcController(PostService service, UserService userService, CommentService commentService, ReactionService reactionService, PostMapper mapper, CommentMapper commentMapper, TagMapper tagMapper, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.userService = userService;
        this.commentService = commentService;
        this.reactionService = reactionService;
        this.mapper = mapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @ModelAttribute("isAdmin")
    public boolean populateIsAdmin(HttpSession session) {
        return session.getAttribute("isAdmin") != null;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping
    public String showAllPosts(@ModelAttribute("postFilterOptions") PostFilterDto filterDto, Model model, HttpSession session) {
        User user;
        PostsFilterOptions filterOptions = new PostsFilterOptions(
                filterDto.getTitle(),
                filterDto.getKeyword(),
                filterDto.getCreatedBy(),
                filterDto.getSortBy(),
                filterDto.getSortOrder());
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
            model.addAttribute("user",user);
            List<Post> posts = service.getAll(filterOptions);
            model.addAttribute("postFilterOptions", filterDto);
            model.addAttribute("posts", posts);
            return "PostsView";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/{id}")
    public String showSinglePost(@PathVariable int id, Model model,HttpSession session) {
        try {
            Post post = service.get(id);
            User user = authenticationHelper.tryGetCurrentUser(session);
            boolean isUpVoted = reactionService.hasUpVoted(post, user);
            model.addAttribute("tags",new ArrayList<>(post.getPostTags()));
            model.addAttribute("upVoteCounter",reactionService.getUpVoteCount(id));
            model.addAttribute("comment",new CommentDto());
            model.addAttribute("isUpVoted",isUpVoted);
            model.addAttribute("post", post);
            model.addAttribute("comments",post.getPostComments());
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
        model.addAttribute("tag",new TagDto());
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
    @PostMapping("{postId}/newComment")
    public String createComment(@Valid @ModelAttribute("comment")CommentDto commentDto,
                                BindingResult errors,
                                HttpSession session, @PathVariable int postId, Model model){
        if (errors.hasErrors()) {
            return "redirect:/posts/{postId}";
        }
        try {
            Comment comment = commentMapper.fromDto(commentDto);
            User user = authenticationHelper.tryGetCurrentUser(session);
            Post post = service.get(postId);
            commentService.create(post,comment,user);
            return "redirect:/posts/{postId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }
    @GetMapping("{postId}/{commentId}/deleteComment")
    public String deleteComment(HttpSession session,@PathVariable int commentId, Model model){
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            commentService.delete(commentId,user);
            return "redirect:/posts/{postId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("{postId}/{commentId}/update")
    public String updateCommentView(HttpSession session,@PathVariable int commentId, Model model){
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            CommentDto commentDto = commentMapper.toDto(commentService.getById(commentId));
            model.addAttribute("commentDto",commentDto);
            return "CommentEdit";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }
    @PostMapping("{postId}/{commentId}/update")
    public String updateComment(HttpSession session,@PathVariable int commentId
            , @Valid @ModelAttribute ("commentDto") CommentDto commentDto, BindingResult errors, Model model){

        if (errors.hasErrors()){
            return "CommentEdit";
        }
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            Comment comment = commentMapper.fromDto(commentId,commentDto);
            commentService.update(comment,user);
            return "redirect:/posts/{postId}";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
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
            model.addAttribute("tag",new TagDto());
            return "PostUpdate";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id,@Valid @ModelAttribute("post") PostDto postDto, BindingResult errors, Model model){
        if(errors.hasErrors()){
            return "PostUpdate";
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
            return "PostUpdate";
        }
    }
    @PostMapping("/{id}/update/addTags")
    public String addTags(@PathVariable int id, @Valid @ModelAttribute("tagDto") TagDto tagDto, Model model){
        try {
            Post post = service.get(id);
            Tag tag = tagMapper.fromDto(tagDto);
            service.addTag(post,tag);
            return "redirect:/posts/{id}";
        } catch (AuthenticationException e) {
            model.addAttribute("statusCode",(HttpStatus.UNAUTHORIZED));
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (InvalidReactionException e){
            model.addAttribute("statusCode",(HttpStatus.BAD_REQUEST));
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
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

    @GetMapping("{postId}/upvote")

    public String upvote(HttpSession session, @PathVariable int postId, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            Post post = service.get(postId);
            service.upvote(post,user);
            return "redirect:/posts/{postId}";
        } catch (AuthenticationException e) {
            model.addAttribute("statusCode",(HttpStatus.UNAUTHORIZED));
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        catch (InvalidReactionException e){
            model.addAttribute("statusCode",(HttpStatus.BAD_REQUEST));
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }

    }


}
