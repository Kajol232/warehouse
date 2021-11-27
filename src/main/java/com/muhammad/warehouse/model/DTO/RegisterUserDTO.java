package com.muhammad.warehouse.model.DTO;

import com.muhammad.warehouse.validator.ValidPassword;

public class RegisterUserDTO {
    private String email;
    @ValidPassword
    private String password;
    private String confirmPassword;
    private String role;
    private boolean isActive;
    private boolean isNotLocked;
    private String username;

    public RegisterUserDTO(String email, String password, String confirmPassword, String role, boolean isActive, boolean isNotLocked, String username) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
