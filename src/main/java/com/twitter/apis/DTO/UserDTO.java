package com.twitter.apis.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    @NotEmpty(message = "Username is required")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", 
    message = "Password must contain at least one number," +
    " one letter, one special character, one capital letter and no whitespace")
    private String password;

    @Email(message = "Email should be valid")
    private String email;
    private String roles;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String email, String roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
