package com.example.forum.filters;

import java.util.Optional;

public class UserPostsFilterOptions {
    private Optional<String> title;
    private Optional<String> content;
    private Optional<String> sortOrder;

    public UserPostsFilterOptions() {
        this(null, null,null);
    }

    public UserPostsFilterOptions(
            String title,
            String content,
            String sortOrder) {
        this.title = Optional.ofNullable(title);
        this.content = Optional.ofNullable(content);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }


    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getContent() {
        return content;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
}
