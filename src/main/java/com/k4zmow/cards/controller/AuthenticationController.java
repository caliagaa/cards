package com.k4zmow.cards.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k4zmow.cards.model.JWTAuthResponse;
import com.k4zmow.cards.model.Login;
import com.k4zmow.cards.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.validator.routines.EmailValidator;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Logs in a user, by using mail and password")
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody Login login){
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        if (!EmailValidator.getInstance().isValid(login.getUsername())) {
            jwtAuthResponse.setMessage("Invalid username. Email is expected");
            return ResponseEntity.badRequest().body(jwtAuthResponse);
        }
        String token = authenticationService.login(login);
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
