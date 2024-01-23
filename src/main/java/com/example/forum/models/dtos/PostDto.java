package com.example.forum.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostDto {
    @NotNull(message = "Post title can't be empty")
    @Size(min = 16, max = 64, message = "Post title should be between 16 and 64 symbols")
    private String title;
    @NotNull(message = "Post content can't be empty")
    @Size(min = 32, max = 8192, message = "Post content should be between 32 and 8192 symbols")
    private String content;

    public PostDto(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
