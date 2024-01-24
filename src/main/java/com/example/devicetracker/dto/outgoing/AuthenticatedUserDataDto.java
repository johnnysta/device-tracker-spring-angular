package com.example.devicetracker.dto.outgoing;

import lombok.Data;

@Data
public class AuthenticatedUserDataDto {
    String userName;
    String email;
    Long id;
}
