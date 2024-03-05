package com.example.userservice.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    @Column(name="username")
    private String userName;
    private String hashedPassword;
    private String email;
    @ManyToMany
    private List<Role> roles;
    private String phone;
    private boolean isEmailVerified;
}
