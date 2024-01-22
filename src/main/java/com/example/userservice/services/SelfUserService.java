package com.example.userservice.services;

import com.example.userservice.exceptions.IncorrectUserCredentials;
import com.example.userservice.exceptions.UserNotFound;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service("selfUserService")
public class SelfUserService implements UserService{
    private UserRepository userRepository;

    @Autowired
    public SelfUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(Long id) throws UserNotFound {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new UserNotFound("User with id " + id + " does not exist");
        }

        User user = userOptional.get();
        return user;
    }
    @Override
    public User addUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) throws UserNotFound {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFound("User with id " + id + " does not exist");
        }

        User finalUser = userOptional.get();

        if(user.getUserName() != null){
            finalUser.setUserName(user.getUserName());
        }
        if(user.getEmail() != null){
            finalUser.setEmail(user.getEmail());
        }
        if(user.getPassword() != null){
            finalUser.setPassword(user.getPassword());
        }
        if(user.getPhone() != null){
            finalUser.setPhone(user.getPhone());
        }

        return userRepository.save(finalUser);
    }

    @Override
    public Long deleteUser(Long id){
//        Optional<User> userOptional = userRepository.removeById(id);

        Optional<Long> userIdOptional = userRepository.removeById(id);
        return userIdOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User login(String username, String password) throws IncorrectUserCredentials {
        Optional<User> userOptional = userRepository.findByUserNameAndPassword(username, password);
        if(userOptional.isEmpty()){
            throw new IncorrectUserCredentials("Incorrect username or password");
        }
        return userOptional.get();
    }
}
