package com.kaleem.springsecurity.service;

import com.kaleem.springsecurity.model.User;
import com.kaleem.springsecurity.repository.UserRepository;
import com.kaleem.springsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(UUID id) {
        return userRepository.getUserById(id);
    }

    public String login(User user) {
        // This will invoke registered auth providers to verify credentials
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (auth.isAuthenticated()) {
            //Generate JWT
            return jwtUtil.generateToken(user);
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }
}
