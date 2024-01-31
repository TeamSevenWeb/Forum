package com.example.forum.filters;

import java.util.Optional;

public class CommentFilterOptions {
    private Optional<Integer> post;
    private Optional<String> keyword;
    private Optional<String> createdBy;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public CommentFilterOptions(
            Integer post,
            String keyword,
            String createdBy,
            String sortBy,
            String sortOrder) {
        this.post = Optional.ofNullable(post);
        this.keyword = Optional.ofNullable(keyword);
        this.createdBy = Optional.ofNullable(createdBy);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<Integer> getPost() {
        return post;
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
