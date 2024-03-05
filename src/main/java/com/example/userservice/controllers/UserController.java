package com.example.userservice.controllers;

import com.example.userservice.dtos.*;
import com.example.userservice.exceptions.IncorrectUserCredentials;
import com.example.userservice.exceptions.UserNotFound;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.SelfUserService;
import com.example.userservice.services.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(@Qualifier("selfUserService") UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupDto signupDto){
        String userName = signupDto.getUserName();
        String email = signupDto.getEmail();
        String password = signupDto.getPassword();
        return UserDto.from(userService.signup(userName, email, password));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequestDto request)
    throws IncorrectUserCredentials{
        return new ResponseEntity<Token>(userService.login(request.getEmail(), request.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request){
        userService.logout(request.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token){
        return UserDto.from(userService.validateToken(token));
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
