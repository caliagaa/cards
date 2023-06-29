package com.k4zmow.cards.service;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.k4zmow.cards.model.Role;
import com.k4zmow.cards.model.User;
import com.k4zmow.cards.repository.UserRepository;
import com.k4zmow.cards.security.CustomUserDetails;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User not exists by Username or Email"));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new CustomUserDetails(
                user.getId(),
                username,
                user.getPassword(),
                authorities
        );
    }

    public User getUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not exists by Username or Email"));
    }

    public User getLoggedUser(Principal principal) {
        User loggedUser = new User();

        UsernamePasswordAuthenticationToken authPrincipal = (UsernamePasswordAuthenticationToken) principal;
        CustomUserDetails user = (CustomUserDetails) authPrincipal.getPrincipal();
        loggedUser.setUsername(user.getUsername());
        loggedUser.setId(user.getId());

        Collection<GrantedAuthority> authorities = authPrincipal.getAuthorities();
        Set<Role> roles = authorities.stream().map(GrantedAuthority::getAuthority)
                .map(s -> new Role(0L, s)).collect(Collectors.toSet());
        loggedUser.setRoles(roles);
        return loggedUser;
    }
}
