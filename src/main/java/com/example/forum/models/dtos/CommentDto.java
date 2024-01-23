package com.example.forum.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentDto {
    @NotNull(message = "Comment can't be empty")
    @Size(min = 2, max = 142, message = "Comment should be between 2 and 142 symbols")
    private String comment;

    public CommentDto(){
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
