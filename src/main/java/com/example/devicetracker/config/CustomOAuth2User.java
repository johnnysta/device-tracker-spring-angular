package com.example.devicetracker.config;

import com.example.devicetracker.domain.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Setter
@Getter
public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oAuth2User;
    private Long userId;

    public CustomOAuth2User(){
        oAuth2User = null;
    }


    public CustomOAuth2User(Account account, OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
        this.userId = account.getId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
}
