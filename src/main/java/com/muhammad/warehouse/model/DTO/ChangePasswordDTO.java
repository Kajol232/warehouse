package com.muhammad.warehouse.model.DTO;

import com.muhammad.warehouse.validator.ValidPassword;

public class ChangePasswordDTO {
    private  String email;
    private String oldPassword;
    @ValidPassword
    private  String password;
    private  String confirmPassword;

    public ChangePasswordDTO(String email, String oldPassword, String password, String confirmPassword) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
}
