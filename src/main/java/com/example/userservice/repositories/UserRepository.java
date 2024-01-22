package com.example.userservice.repositories;

import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<Long> removeById(Long id);

    Optional<User> findByUserNameAndPassword(String userName,String Password);

    User save(User user);


}
