package com.example.userservice.dtos;

import com.example.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String userName;
    private String email;
    private String password;
}
