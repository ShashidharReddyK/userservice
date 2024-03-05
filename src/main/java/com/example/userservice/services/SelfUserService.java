package com.example.userservice.services;

import com.example.userservice.dtos.SignupDto;
import com.example.userservice.exceptions.IncorrectUserCredentials;
import com.example.userservice.exceptions.UserNotFound;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service("selfUserService")
public class SelfUserService implements UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    @Autowired
    public SelfUserService(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }
    @Override
    public User signup(String userName, String email, String password){
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
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
        if(user.getHashedPassword() != null){
            finalUser.setHashedPassword(user.getHashedPassword());
        }
        if(user.getPhone() != null){
            finalUser.setPhone(user.getPhone());
        }

        return userRepository.save(finalUser);
    }

    @Override
    public Token login(String email, String password) throws IncorrectUserCredentials {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new IncorrectUserCredentials("Incorrect username or password");
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            return null;
        }

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(32));

        tokenRepository.save(token);

        return token;
    }

    public void logout(String token){
        Optional<Token> token1 = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThanEqual(token, false, new Date());

        if(token.isEmpty()){
            return ;
        }
        Token token2 = token1.get();
        token2.setDeleted(true);
        tokenRepository.save(token2);
    }

    public User validateToken(String token){
        Optional<Token> token1 = tokenRepository.findByValueAndDeletedEqualsAndExpiryAtGreaterThanEqual(token, false, new Date());

        if(token1.isEmpty()){
            return null;
        }
        return token1.get().getUser();
    }
}
