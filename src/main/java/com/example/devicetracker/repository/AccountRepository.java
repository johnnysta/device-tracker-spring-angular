package com.example.devicetracker.repository;

import com.example.devicetracker.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByGoogleUser(String googleUser);

    boolean existsAccountByGitHubUser(String gitHubUser);

    Account findByGoogleUser(String oAuthGmail);

    Account findByGitHubUser(String oAuthGitHubUser);
}
