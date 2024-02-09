package com.example.forum.controllers.mvc;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.exceptions.EntityDuplicateException;
import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.helpers.AuthenticationHelper;
import com.example.forum.helpers.UserMapper;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.models.dtos.PostDto;
import com.example.forum.models.dtos.UserDto;
import com.example.forum.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserMvcController {

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


//    @GetMapping
//    public String showAllUsers(@ModelAttribute("userFilterOptions") UserFilterDto userFilterDto, Model model, HttpSession session) {
//       UserFilterOptions userFilterOptions = new UserFilterOptions(
//                userFilterDto.getUsername(),
//                userFilterDto.getEmail(),
//                userFilterDto.getFirstName(),
//                userFilterDto.getSortBy(),
//                userFilterDto.getSortOrder());
//        List<User> users = userService.getAll(userFilterOptions);
//        if (populateIsAuthenticated(session)){
//            String currentUsername = (String) session.getAttribute("currentUser");
//            model.addAttribute("currentUser", userService.getByUsername(currentUsername));
//        }
//        model.addAttribute("userFilterOptions", userFilterDto);
//        model.addAttribute("users", users);
//        return "UsersView";
//    }

    @GetMapping
    public String showUserOwnPage(Model model, HttpSession session){
        User user;
       try {
           user = authenticationHelper.tryGetCurrentUser(session);
           model.addAttribute("user",user);
           return "UserView";
       } catch (AuthorizationException e) {
           return "redirect:/auth/login";
       }
    }

    @GetMapping("/update")
    public String showEditBeerPage(Model model, HttpSession session) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            UserDto userDto = userMapper.toDto(user);
            model.addAttribute("userId", user.getId());
            model.addAttribute("user", userDto);
            return "UserUpdateView";
        }catch (AuthorizationException e){
            return "redirect:/auth/login";
        }
    }

    @PostMapping("/update")
    public String updatePost(@Valid @ModelAttribute("user") UserDto userDto, BindingResult errors, Model model,HttpSession session){
        if(errors.hasErrors()){
            return "UserView";
        }
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            User userToUpdate = userMapper.fromDto(user.getId(),userDto);
            userService.update(user,userToUpdate);
            return "redirect:/user";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }catch (EntityDuplicateException e){
            errors.rejectValue("name","post.exists",e.getMessage());
            return "UserUpdateView";
        }
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable int id, Model model, HttpSession session){
        User userToShow;
        try {
            User loggedUser = authenticationHelper.tryGetCurrentUser(session);
            userToShow = userService.get(id);
            model.addAttribute("user",userToShow);
            return "UserView";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e){
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }
}
