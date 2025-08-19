package com.vitran.backend.controller;

import com.vitran.backend.dto.AuthRequest;
import com.vitran.backend.dto.LoginResponse;
import com.vitran.backend.model.UserLogin;
import com.vitran.backend.security.JwtTokenService;
import com.vitran.backend.service.UserLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/worker")
public class AuthController {

    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;

    public AuthController(UserLoginService userLoginService, JwtTokenService jwtTokenService) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest req) {
        System.out.println("AuthController /worker/login called: " + req.userLoginId());
        if (req == null || req.userLoginId() == null || req.password() == null) return ResponseEntity.badRequest().build();
        return userLoginService.authenticateAndProfile(req.userLoginId(), req.password())
                .map(profile -> {
                    UserLogin user = userLoginService.findById(profile.userLoginId());
                    var perms = userLoginService.extractPermissionIds(user);

                    String token = jwtTokenService.issueAccessToken(
                            profile.userLoginId(),
                            profile.partyId(),
                            profile.firstName(),
                            perms
                    );
                    return ResponseEntity.ok(new LoginResponse(profile, token));
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}