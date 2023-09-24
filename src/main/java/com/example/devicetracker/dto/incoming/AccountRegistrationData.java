package com.example.devicetracker.dto.incoming;

import lombok.Data;

@Data
public class AccountRegistrationData {
    private String user_name;
    private String user_email;
    private String user_password;

}
