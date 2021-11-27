package com.muhammad.warehouse.controller;

import com.muhammad.warehouse.config.jwt.JWTProvider;
import com.muhammad.warehouse.exception.domain.EmailExistException;
import com.muhammad.warehouse.exception.domain.InvalidLoginException;
import com.muhammad.warehouse.exception.domain.UserNotFoundException;
import com.muhammad.warehouse.exception.domain.UsernameExistException;
import com.muhammad.warehouse.model.DTO.RegisterUserDTO;
import com.muhammad.warehouse.model.DTO.UserLoginDTO;
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
    private JWTProvider provider;
    private UserRepository userRepository;
    private IUserService userService;


    public UserController(JWTProvider provider, UserRepository repository, IUserService userService) {
        this.provider = provider;
        this.userRepository = repository;
        this.userService = userService;
    }

    @GetMapping(path = "/lists", consumes = "application/json", produces = "application/json")
    public List<User> getAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    @GetMapping(path = "/getUserById/{id}", consumes = "application/json", produces = "application/json")
    public User getUserById(@PathVariable("id") long id){
        return userRepository.findById(id).get();
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO userDto) throws UserNotFoundException, UsernameExistException, EmailExistException, InvalidLoginException {
        User user = userService.register(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    ResponseEntity<Object> userLogin(@RequestBody UserLoginDTO loginDTO){
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
