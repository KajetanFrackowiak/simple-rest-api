package com.example.demo.controller;

import com.example.demo.model.UserAccount;
import com.example.demo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping
    public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {
        if (userAccount.getUsername() == null || userAccount.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        UserAccount createdUserAccount = userAccountService.createUserAccount(
                userAccount.getUsername(), userAccount.getGender(), userAccount.getAge());
        return ResponseEntity.ok(createdUserAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserAccount(@PathVariable Long id) {
        Optional<UserAccount> userAccountOptional = userAccountService.getUserAccount(id);
        return userAccountOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccount> updateUserAccount(@PathVariable Long id, @RequestBody UserAccount userAccount) {
        UserAccount updatedUserAccount = userAccountService.updateUserAccount(id, userAccount.getUsername(), userAccount.getGender(), userAccount.getAge());
        if (updatedUserAccount == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUserAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
        userAccountService.deleteUserAccount(id);
        return ResponseEntity.noContent().build();
    }
}