package com.k4zmow.cards.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.k4zmow.cards.model.Login;
import com.k4zmow.cards.repository.UserRepository;
import com.k4zmow.cards.security.JwtTokenProvider;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(Login login) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}
