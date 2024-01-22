package com.example.userservice.services;

import com.example.userservice.exceptions.IncorrectUserCredentials;
import com.example.userservice.exceptions.UserNotFound;
import com.example.userservice.models.User;

import java.util.List;

public interface UserService {
    User getUser(Long id) throws UserNotFound;
    User addUser(User user);
    User updateUser(Long id, User user) throws UserNotFound;
    Long deleteUser(Long id);
    List<User> getAllUsers();

    User login(String username, String password) throws IncorrectUserCredentials;


}
