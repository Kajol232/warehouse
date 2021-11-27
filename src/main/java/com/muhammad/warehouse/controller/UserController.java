package com.muhammad.warehouse.controller;

import com.muhammad.warehouse.config.jwt.JWTProvider;
import com.muhammad.warehouse.exception.domain.*;
import com.muhammad.warehouse.model.DTO.*;
import com.muhammad.warehouse.model.User;
import com.muhammad.warehouse.model.UserPrincipal;
import com.muhammad.warehouse.repository.UserRepository;
import com.muhammad.warehouse.service.IUserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muhammad.warehouse.config.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final JWTProvider provider;
    private final UserRepository userRepository;
    private final IUserService userService;


    public UserController(JWTProvider provider, UserRepository repository, IUserService userService) {
        this.provider = provider;
        this.userRepository = repository;
        this.userService = userService;
    }

    @GetMapping(path = "/lists", consumes = "application/json", produces = "application/json")
    public List<User> getAllUsers(){
        return (List<User>) userService.getUsers();
    }

    @GetMapping(path = "/getById/{id}", consumes = "application/json", produces = "application/json")
    public User getUserById(@PathVariable("id") long id){
        return userRepository.findById(id).get();
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO userDto) throws UserNotFoundException, UsernameExistException, EmailExistException, InvalidLoginException {
        User user = userService.register(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UpdateUserDTO userDTO) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User user = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PatchMapping(path = "/changePassword", consumes = "application/json", produces = "application/json")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws EmailNotFoundException, InvalidLoginException {
        userService.changePassword(changePasswordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping(path = "/resetPassword", consumes = "application/json", produces = "application/json")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) throws EmailNotFoundException, InvalidLoginException {
        userService.resetPassword(resetPasswordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    ResponseEntity<User> userLogin(@RequestBody UserLoginDTO loginDTO){
       User user = userService.findUserByEmail(loginDTO.getEmail());
        UserPrincipal userPrincipal = new UserPrincipal(user);
        HttpHeaders headers = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, provider.generateToken(user));
        return headers;
    }

}
