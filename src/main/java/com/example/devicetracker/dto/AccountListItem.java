package com.example.devicetracker.dto;

import com.example.devicetracker.domain.Account;
import lombok.Data;

@Data
public class AccountListItem {

    private Long user_id;
    private String user_name;
    private String user_email;


    public AccountListItem(Account account) {
        this.user_name = account.getUserName();
        this.user_email = account.getEmail();
        this.user_id = account.getId();
    }
}
