package com.example.forum.filters;

import java.util.Optional;

public class PostsFilterOptions {

    private Optional<String> title;
    private Optional<String> keyword;
    private Optional<String> createdBy;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public PostsFilterOptions(
            String title,
            String keyword,
            String createdBy,
            String sortBy,
            String sortOrder) {
        this.title = Optional.ofNullable(title);
        this.keyword = Optional.ofNullable(keyword);
        this.createdBy = Optional.ofNullable(createdBy);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getKeyword() {
        return keyword;
    }

    public Optional<String> getCreatedBy() {
        return createdBy;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

}
