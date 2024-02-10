package com.example.forum.controllers.mvc;

import com.example.forum.models.Post;
import com.example.forum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final PostService postService;
    @Autowired
    public HomeMvcController(PostService postService) {
        this.postService = postService;
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
}
