package com.example.forum.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TagDto {
    @NotNull(message = "Comment can't be empty")
    private String name;

    public TagDto(){
    }

    public String getTagName() {
        return name;
    }

    public void setTagName(String tagName) {
        this.name = tagName;
    }
}
