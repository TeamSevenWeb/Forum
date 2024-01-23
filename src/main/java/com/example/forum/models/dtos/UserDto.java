package com.example.forum.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotNull(message = "Username can't be empty")
    @Size(min = 4, max = 32, message = "Username should be between 4 and 32 symbols")
    private String username;
    @NotNull(message = "Password can't be empty")
    @Size(min = 8, max = 32, message = "Password should be between 8 and 32 symbols")
    private String password;
    @NotNull(message = "First name can't be empty")
    @Size(min = 4, max = 32, message = "First name should be between 4 and 32 symbols")
    private String firstName;
    @NotNull(message = "Last name can't be empty")
    @Size(min = 4, max = 32, message = "Last name should be between 4 and 32 symbols")
    private String lastName;
    @NotNull(message = "Email can't be empty")
    @Size(min = 4, max = 32, message = "Email should be between 4 and 32 symbols")
    private String email;

    public UserDto(){
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
