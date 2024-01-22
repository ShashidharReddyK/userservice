package com.example.userservice.controllers;

import com.example.userservice.dtos.ExceptionDto;
import com.example.userservice.exceptions.IncorrectUserCredentials;
import com.example.userservice.exceptions.UserNotFound;
import com.example.userservice.models.User;
import com.example.userservice.services.SelfUserService;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user-{id}")
    public ResponseEntity<User> getUserByIdResponse(@PathVariable("id") Long id) throws UserNotFound {
        return new ResponseEntity<User>(
                userService.getUser(id),
                HttpStatus.OK);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PatchMapping("/patch-{id}")
    public User updateUser(@PathVariable("id") Long id,@RequestBody User user) throws UserNotFound {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete-{id}")
    public Long deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/login-{username}-{password}")
    public ResponseEntity<User> login(@PathVariable("username") String username, @PathVariable("password") String password)
    throws IncorrectUserCredentials{
        return new ResponseEntity<User>(
                userService.login(username, password),
                HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ExceptionDto> handleUserNotFound(UserNotFound userNotFound){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(userNotFound.getMessage());
        return new ResponseEntity<ExceptionDto>(exceptionDto, HttpStatus.OK);
    }

    @ExceptionHandler(IncorrectUserCredentials.class)
    public ResponseEntity<ExceptionDto> handleIncorrectUserCredentials(IncorrectUserCredentials incorrectUserCredentials){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(incorrectUserCredentials.getMessage());
        return new ResponseEntity<ExceptionDto>(exceptionDto, HttpStatus.OK);
    }

}
