package com.usuario.service;

import com.usuario.converter.UserConverter;
import com.usuario.dto.user.UserRequestDTO;
import com.usuario.dto.user.UserResponseDTO;
import com.usuario.entity.User;
import com.usuario.exceptions.EmailAlreadyExistsException;
import com.usuario.exceptions.ResourceNotFoundException;
import com.usuario.repository.UserRepository;
import com.usuario.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //USER
    @Transactional(readOnly = true)
    public UserResponseDTO findAuthenticatedUser(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));

        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("User not found."));

        return userConverter.toUserDTO(user);
    }

    @Transactional
    public UserResponseDTO updateAuthenticatedUser(String token, UserRequestDTO requestDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));

        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("User not found."));

        validateEmailUpdate(requestDTO.getEmail(), user.getEmail());

        user.setName(requestDTO.getName() != null ? requestDTO.getName() : user.getName());
        user.setEmail(requestDTO.getEmail() != null ? requestDTO.getEmail() : user.getEmail());
        user.setPassword(requestDTO.getPassword() != null ?
                passwordEncoder.encode(requestDTO.getPassword()) : user.getPassword());

        return userConverter.toUserDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteAuthenticatedUser(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));

        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("User not found."));

        userRepository.delete(user);
    }

    //ADMIN
    @Transactional(readOnly = true)
    public UserResponseDTO findUserById(Long id) {
        User user = findUserEntityById(id);

        return userConverter.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userConverter::toUserDTO)
                .toList();
    }

    @Transactional
    public UserResponseDTO updateUserById(Long id, UserRequestDTO requestDTO) {
        User user = findUserEntityById(id);

        validateEmailUpdate(requestDTO.getEmail(), user.getEmail());

        user.setName(requestDTO.getName() != null ? requestDTO.getName() : user.getName());
        user.setEmail(requestDTO.getEmail() != null ? requestDTO.getEmail() : user.getEmail());
        user.setPassword(requestDTO.getPassword() != null ?
                passwordEncoder.encode(requestDTO.getPassword()) : user.getPassword());

        return userConverter.toUserDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = findUserEntityById(id);

        userRepository.delete(user);
    }

    //MÉTODOS PRIVADOS
    private void validateEmailUpdate(String newEmail, String savedEmail) {
        if (newEmail == null || newEmail.equals(savedEmail)) {
            return;
        }

        if (userRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException("Email already registered: " + newEmail);
        }
    }

    private User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }
}


