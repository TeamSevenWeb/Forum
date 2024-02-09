package com.example.forum.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @NotEmpty(message = "Username can't be empty")
    @Size(min = 4, max = 32, message = "Username should be between 4 and 32 symbols")
    private String username;

    @NotEmpty(message = "Password can't be empty")
    @Size(min = 8, max = 32, message = "Password should be between 8 and 32 symbols")
    private String password;

    public LoginDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

