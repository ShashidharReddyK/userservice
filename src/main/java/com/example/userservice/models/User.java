package com.example.userservice.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    @Column(name="username")
    private String userName;
    private String password;
    private String email;
    private String phone;
}
