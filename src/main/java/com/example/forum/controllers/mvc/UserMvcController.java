package com.example.forum.controllers.mvc;

import com.example.forum.exceptions.*;
import com.example.forum.filters.UserFilterOptions;
import com.example.forum.filters.UserPostsFilterOptions;
import com.example.forum.filters.dtos.UserFilterDto;
import com.example.forum.filters.dtos.UserPostsFilterDto;
import com.example.forum.helpers.AuthenticationHelper;
import com.example.forum.helpers.UserMapper;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.models.dtos.RegisterDto;
import com.example.forum.models.dtos.UserDto;
import com.example.forum.models.dtos.UserUpdateDto;
import com.example.forum.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserMvcController {

    public static final String NOT_AUTHORIZED = "Not authorized";
    public static final String UNAUTHORIZED = "Unauthorized";
    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthenticationHelper authenticationHelper;

    public UserMvcController(UserService userService, UserMapper userMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @ModelAttribute("userId")
    public boolean populateUserId(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    @ModelAttribute("isAdmin")
    public boolean populateIsAdmin(HttpSession session) {
        return session.getAttribute("isAdmin") != null;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @GetMapping("/all")
    public String showAllUsers(@ModelAttribute("userFilterOption") UserFilterDto filterDto
            , Model model, HttpSession session) {
        User user;
        UserFilterOptions userFilterOptions = new UserFilterOptions(
                filterDto.getUsername(),
                filterDto.getEmail(),
                filterDto.getFirstName(),
                filterDto.getSortBy(),
                filterDto.getSortOrder());
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
            model.addAttribute("user", user);
            List<User> users = userService.getAll(userFilterOptions, user);
            model.addAttribute("userFilterOptions", filterDto);
            model.addAttribute("users", users);
            return "AllUsersView";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping
    public String showUserOwnPage(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
            model.addAttribute("user", user);
            return "UserView";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable int id, Model model, HttpSession session) {
        User userToShow;
        try {
            authenticationHelper.tryGetCurrentUser(session);
            userToShow = userService.get(id);
            model.addAttribute("user", userToShow);
            return "UserView";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/posts")
    public String showUserPosts(@ModelAttribute("userPostsFilterOption") UserPostsFilterDto filterDto
            ,@PathVariable int id, Model model, HttpSession session) {
        User userToShow;
        List<Post>userPosts;
        try {
            authenticationHelper.tryGetCurrentUser(session);
            userToShow = userService.get(id);
            model.addAttribute("user", userToShow);
            userPosts = userService.getUserPosts(filterDto,userToShow);
            model.addAttribute("userPostsFilterOption", filterDto);
            model.addAttribute("posts", userPosts);
            return "UserPostsView";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @GetMapping("/{id}/comments")
    public String showUserComments(@ModelAttribute("userPostsFilterOption") UserPostsFilterDto filterDto
            ,@PathVariable int id, Model model, HttpSession session) {
        User userToShow;
        List<Comment>userComments;
        try {
            authenticationHelper.tryGetCurrentUser(session);
            userToShow = userService.get(id);
            model.addAttribute("user", userToShow);
            userComments = userService.getUserComments(filterDto,userToShow);
            model.addAttribute("userPostsFilterOption", filterDto);
            model.addAttribute("comments", userComments);
            return "UserCommentsView";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }


    @GetMapping("/{id}/update")
    public String showUpdateUserPage(@PathVariable int id, Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            User userToUpdate = userService.get(id);
            if (!user.isAdmin() && user.getId() != id) {
                throw new AuthorizationException(NOT_AUTHORIZED);
            }
            UserUpdateDto userDto = userMapper.toUpdateDto(userToUpdate);
            model.addAttribute("userId", id);
            model.addAttribute("user", userDto);
            return "UserUpdateView";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{id}/update")
    public String updateUserView(@PathVariable int id
            , @Valid @ModelAttribute("user") UserUpdateDto userDto
            , BindingResult errors
            , Model model
            , HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
            User userToUpdate = userService.get(id);
            if (!user.isAdmin() && user.getId() != id) {
                throw new AuthorizationException(NOT_AUTHORIZED);
            }
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
        if (errors.hasErrors()) {
            return "UserUpdateView";
        }

        try {
            User userToUpdate = userMapper.fromUpdateDto(id, userDto);
            userService.update(userToUpdate, user);
            return "redirect:/user";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("email", "email.exists", e.getMessage());
            return "UserUpdateView";
        }
    }

    @GetMapping("{id}/update/block")

    public String blockAndUnblockUser(HttpSession session, @PathVariable int id, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if (!user.isAdmin()){
                throw new AuthorizationException(UNAUTHORIZED);
            }
            User userToUpdate = userService.get(id);
            userService.block(id, user);
            return "redirect:/user/{id}/update";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", (HttpStatus.UNAUTHORIZED));
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }

    }

    @GetMapping("{id}/update/admin")

    public String makeAdminAndNotAdmin(HttpSession session, @PathVariable int id, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if (!user.isAdmin()){
                throw new AuthorizationException(UNAUTHORIZED);
            }
            User userToUpdate = userService.get(id);
            userService.makeAdmin(id, user);
            return "redirect:/user/{id}/update";
        } catch (AuthenticationException e) {
            return "redirect:/auth/login";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", (HttpStatus.UNAUTHORIZED));
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }

    }
}
