package com.example.devicetracker.domain;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_name")
    String userName;

    @Column(name = "user_email", unique = true)
    String email;

    @Column(name="user_password")
    String password;


}
