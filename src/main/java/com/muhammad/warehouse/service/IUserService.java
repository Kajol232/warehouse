package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.domain.*;
import com.muhammad.warehouse.model.DTO.RegisterUserDTO;
import com.muhammad.warehouse.model.DTO.ChangePasswordDTO;
import com.muhammad.warehouse.model.DTO.ResetPasswordDTO;
import com.muhammad.warehouse.model.DTO.UpdateUserDTO;
import com.muhammad.warehouse.model.User;

import java.util.List;

public interface IUserService {
    User register(RegisterUserDTO registerUser) throws UserNotFoundException, UsernameExistException, EmailExistException, InvalidLoginException;
    List<User> getUsers();
    User findUserByEmail(String email);
    User updateUser(long id, UpdateUserDTO updateUserDTO)throws UserNotFoundException, UsernameExistException, EmailExistException;
    void deleteUser(long id);
    void changePassword(ChangePasswordDTO changePasswordDTO) throws EmailNotFoundException, InvalidLoginException;
    void resetPassword(ResetPasswordDTO resetPasswordDTO)throws EmailNotFoundException;
}
