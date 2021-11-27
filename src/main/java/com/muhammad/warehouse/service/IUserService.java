package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.domain.*;
import com.muhammad.warehouse.model.DTO.RegisterUserDTO;
import com.muhammad.warehouse.model.User;

import java.util.List;

public interface IUserService {
    User register(RegisterUserDTO registerUser) throws UserNotFoundException, UsernameExistException, EmailExistException, UserNotFoundException, UsernameExistException, EmailExistException, InvalidLoginException;
    List<User> getUsers();
    User findUserByEmail(String email);
    User updateUser()throws UserNotFoundException, UsernameExistException, EmailExistException;
    void deleteUser(long id);
    void resetPassword(String email, String password) throws EmailNotFoundException;;
}
