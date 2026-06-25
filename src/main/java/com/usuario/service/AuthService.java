package com.usuario.service;

import com.usuario.converter.UserConverter;
import com.usuario.dto.auth.AuthRequestDTO;
import com.usuario.dto.auth.AuthResponseDTO;
import com.usuario.dto.user.UserRequestDTO;
import com.usuario.dto.user.UserResponseDTO;
import com.usuario.entity.User;
import com.usuario.entity.enums.Role;
import com.usuario.exceptions.EmailAlreadyExistsException;
import com.usuario.repository.UserRepository;
import com.usuario.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        validateExistingEmail(userRequestDTO.getEmail());

        User user = userConverter.toUserEntity(userRequestDTO);

        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);

        return userConverter.toUserDTO(savedUser);
    }

    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));

        User user = userRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        String token = jwtUtil.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    private void validateExistingEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already registered: " + email);
        }
    }
}


