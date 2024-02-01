package com.example.devicetracker.dto.out;

import lombok.Data;

@Data
public class AuthenticatedUserDataDto {
    String userName;
    String email;
    Long id;
}
