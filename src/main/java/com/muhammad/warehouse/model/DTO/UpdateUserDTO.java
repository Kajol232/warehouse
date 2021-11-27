package com.muhammad.warehouse.model.DTO;


public class UpdateUserDTO {
    private String email;
    private String role;
    private boolean isActive;
    private boolean isNotLocked;

    public UpdateUserDTO(String email,String role, boolean isActive, boolean isNotLocked) {
        this.email = email;
        this.role = role;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
