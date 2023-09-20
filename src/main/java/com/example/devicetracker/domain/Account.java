package com.example.devicetracker.domain;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "user_name")
    String usrName;

    @Column(name = "user_email")
    String email;

    @Column(name="user_password")
    String password;


}
