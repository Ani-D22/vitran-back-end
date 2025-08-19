package com.vitran.backend.service;

import com.vitran.backend.dto.AuthProfile;
import com.vitran.backend.model.Enumeration;
import com.vitran.backend.model.UserLogin;
import com.vitran.backend.repo.UserLoginRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserLoginService {

    private final UserLoginRepo userLoginRepo;
    private final PasswordEncoder passwordEncoder;

    public UserLoginService(UserLoginRepo userLoginRepo, PasswordEncoder passwordEncoder) {
        this.userLoginRepo = userLoginRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserLogin findById(String userLoginId) {
        return userLoginRepo.findById(userLoginId)
                .orElseThrow(() -> new NoSuchElementException("UserLogin not found: " + userLoginId));
    }

    public Optional<UserLogin> findOpt(String userLoginId) {
        return userLoginRepo.findById(userLoginId);
    }

    public Iterable<UserLogin> findAll() {
        return userLoginRepo.findAll();
    }

    public boolean verifyCredentials(String userLoginId, String rawPassword) {
        return userLoginRepo.findById(userLoginId)
                .filter(u -> u.getPassword() != null && passwordEncoder.matches(rawPassword, u.getPassword()))
                .isPresent();
    }

    public Optional<AuthProfile> authenticateAndProfile(String userLoginId, String rawPassword) {
        return userLoginRepo.findById(userLoginId)
                .filter(u -> u.getPassword() != null && passwordEncoder.matches(rawPassword, u.getPassword()))
                .map(u -> new AuthProfile(
                        u.getUserLoginId(),
                        u.getParty() != null ? u.getParty().getPartyId() : null,
                        u.getParty() != null ? u.getParty().getFirstName() : null
                ));
    }

    public Set<String> extractPermissionIds(UserLogin user) {
        Set<Enumeration> perms = user.getPermissions();
        if (perms == null) return Set.of();
        return perms.stream()
                .map(Enumeration::getEnumId) // enumId values from enumeration table
                .collect(Collectors.toSet());
    }
}

