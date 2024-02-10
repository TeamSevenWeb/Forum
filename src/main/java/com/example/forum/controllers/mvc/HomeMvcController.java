package com.example.forum.controllers.mvc;

import com.example.forum.exceptions.AuthorizationException;
import com.example.forum.helpers.AuthenticationHelper;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import com.example.forum.services.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeMvcController {
    private final AuthenticationHelper authenticationHelper;

    private final PostService postService;
    @Autowired
    public HomeMvcController(AuthenticationHelper authenticationHelper, PostService postService) {
        this.authenticationHelper = authenticationHelper;
        this.postService = postService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showHomePage(Model model) {
        List<Post> mostCommentedPosts = postService.getMostCommentedPosts();
        List<Post> mostRecentPosts = postService.getTenMostRecentPosts();
        model.addAttribute("mostCommentedPosts", mostCommentedPosts);
        model.addAttribute("mostRecentPosts", mostRecentPosts);
        return "HomeView";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "AboutView";
    }

    @GetMapping("/admin")
    public String showAdminPortal(HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if(user.isAdmin()){
                return "AdminPortalView";
            }
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "ErrorView";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }
    }
}
