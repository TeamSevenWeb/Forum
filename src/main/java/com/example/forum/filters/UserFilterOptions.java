package com.example.forum.filters;

import java.util.Optional;

public class UserFilterOptions {
    private Optional<String> username;
    private Optional<String> email;
    private Optional<String> firstName;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public UserFilterOptions() {
        this(null, null, null, null, null);
    }

    public UserFilterOptions(
            String username,
            String email,
            String firstName,
            String sortBy,
            String sortOrder) {
        this.username = Optional.ofNullable(username);
        this.email = Optional.ofNullable(email);
        this.firstName = Optional.ofNullable(firstName);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getFirstName() {
        return firstName;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

}
