package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.domain.*;
import com.muhammad.warehouse.model.DTO.RegisterUserDTO;
import com.muhammad.warehouse.model.DTO.ChangePasswordDTO;
import com.muhammad.warehouse.model.DTO.ResetPasswordDTO;
import com.muhammad.warehouse.model.DTO.UpdateUserDTO;
import com.muhammad.warehouse.model.Role;
import com.muhammad.warehouse.model.User;
import com.muhammad.warehouse.model.UserPrincipal;
import com.muhammad.warehouse.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.muhammad.warehouse.config.constant.UserImplConstant.EMAIL_ALREADY_EXISTS;
import static com.muhammad.warehouse.model.Role.ROLE_ADMIN;
import static com.muhammad.warehouse.model.Role.ROLE_WORKER_USER;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
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
    public User register(RegisterUserDTO registerUser) throws EmailExistException, InvalidLoginException {
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
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User updateUser(long id, UpdateUserDTO updateUserDTO) throws UserNotFoundException, EmailExistException {
        User user = userRepository.findUserById(id);
        User userByNewEmail = findUserByEmail(updateUserDTO.getEmail());

        if(user == null) {
            throw new UserNotFoundException("User does not exist");
        }
        if(!updateUserDTO.getEmail().isEmpty()) {
            if (userByNewEmail != null && !(user.getId() == (userByNewEmail.getId()))) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            user.setEmail(updateUserDTO.getEmail());
        }
        if(updateUserDTO.getRole() != null && !updateUserDTO.getRole().isEmpty()){
            Role r = getRoleEnumName(updateUserDTO.getRole());
            user.setRole(r.name());
            user.setAuthorities(r.getAuthorities());

        }
        user.setActive(updateUserDTO.isActive());
        user.setNotLocked(updateUserDTO.isNotLocked());

        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws EmailNotFoundException, InvalidLoginException {
        User user = userRepository.findUserByEmail(changePasswordDTO.getEmail());
        if(user == null){
            throw new EmailNotFoundException("No user for Email: " + changePasswordDTO.getEmail());
        }
        if(!passwordEncoder.matches(user.getPassword(), changePasswordDTO.getOldPassword())){
           throw new InvalidLoginException("Invalid Password");
        }
        if(changePasswordDTO.getPassword().isEmpty() || changePasswordDTO.getPassword().isEmpty() ||
                changePasswordDTO.getEmail().isEmpty()){
            throw new InvalidLoginException("New Password cannot be blank");
        }
        if (!changePasswordDTO.getConfirmPassword().equals(changePasswordDTO.getPassword())){
            throw new InvalidLoginException("Password Not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userRepository.save(user);

    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws EmailNotFoundException {
        User user = userRepository.findUserByEmail(resetPasswordDTO.getEmail());
        if(user == null){
            throw new EmailNotFoundException("No user for Email: " + resetPasswordDTO.getEmail());
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userRepository.save(user);

    }


    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());

    }

}
