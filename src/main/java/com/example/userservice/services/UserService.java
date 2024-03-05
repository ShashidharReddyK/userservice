package com.example.userservice.services;

import com.example.userservice.dtos.SignupDto;
import com.example.userservice.exceptions.IncorrectUserCredentials;
import com.example.userservice.exceptions.UserNotFound;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;

import java.util.List;

public interface UserService {

    User signup(String userName, String email, String password);

    Token login(String email, String password) throws IncorrectUserCredentials;

    User updateUser(Long is, User user) throws UserNotFound;

    void logout(String token);

    User validateToken(String token);


}
