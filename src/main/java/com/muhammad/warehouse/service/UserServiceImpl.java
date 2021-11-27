package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.domain.*;
import com.muhammad.warehouse.model.DTO.RegisterUserDTO;
import com.muhammad.warehouse.model.Role;
import com.muhammad.warehouse.model.User;
import com.muhammad.warehouse.model.UserPrincipal;
import com.muhammad.warehouse.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.muhammad.warehouse.model.Role.ROLE_ADMIN;
import static com.muhammad.warehouse.model.Role.ROLE_WORKER_USER;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found by username: " + email);
        }else{
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
    }

    @Override
    public User register(RegisterUserDTO registerUser) throws UserNotFoundException, UsernameExistException, EmailExistException, UserNotFoundException, UsernameExistException, EmailExistException, InvalidLoginException {
        if(userRepository.existsByEmail(registerUser.getEmail())){
            throw new EmailExistException(registerUser.getEmail() + " already exists");
        }
        if(!registerUser.getEmail().contains("@") || !registerUser.getEmail().contains(".")){
            throw new InvalidLoginException("Invalid Email");
        }
        if(registerUser.getPassword().isEmpty() || registerUser.getPassword().isEmpty() ||
                registerUser.getEmail().isEmpty()){
            throw new InvalidLoginException("Password cannot be blank");
        }
        if (!registerUser.getConfirmPassword().equals(registerUser.getPassword())){
            throw new InvalidLoginException("Password Not match");
        }

        User user = new User();
        user.setEmail(registerUser.getEmail());
        user.setUsername(registerUser.getUsername());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setActive(registerUser.isActive());
        user.setNotLocked(registerUser.isNotLocked());

        String role = registerUser.getRole();

        if(role.equalsIgnoreCase("Worker")){
            user.setRole(ROLE_WORKER_USER.name());
            user.setAuthorities(ROLE_WORKER_USER.getAuthorities());
        }
        if(role.equalsIgnoreCase("Admin")){
            user.setRole(ROLE_ADMIN.name());
            user.setAuthorities(ROLE_ADMIN.getAuthorities());
        }

        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User updateUser() throws UserNotFoundException, UsernameExistException, EmailExistException {
        return null;
    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public void resetPassword(String email, String password) throws EmailNotFoundException {

    }


    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());

    }
}
