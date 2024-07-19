package com.example.demo.service;

import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccount createUserAccount(String username, String gender, int age) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setGender(gender);
        userAccount.setAge(age);
        userAccount.setCreatedTimestamp(LocalDateTime.now());
        return userAccountRepository.save(userAccount);
    }

    public Optional<UserAccount> getUserAccount(Long id) {
        return userAccountRepository.findById(id);
    }

    public UserAccount updateUserAccount(Long id, String username, String gender, int age) {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findById(id);
        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();
            if (username != null) userAccount.setUsername(username); // Ensuring username is updated correctly.
            if (gender != null) userAccount.setGender(gender);
            if (age > 0) userAccount.setAge(age);
            return userAccountRepository.save(userAccount); // Corrected method call.
        }
        return null;
    }

    public void deleteUserAccount(Long id) {
        userAccountRepository.deleteById(id);
    }
}