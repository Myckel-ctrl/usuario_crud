package com.usuario.controller;

import com.usuario.dto.user.UserRequestDTO;
import com.usuario.dto.user.UserResponseDTO;
import com.usuario.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //USUÁRIO AUTENTICADO
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> findAuthenticatedUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.findAuthenticatedUser(token));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateAuthenticatedUser(@RequestHeader("Authorization") String token,
                                                                   @Valid @RequestBody UserRequestDTO requestDTO) {

        return ResponseEntity.ok(userService.updateAuthenticatedUser(token, requestDTO));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAuthenticatedUser(@RequestHeader("Authorization") String token) {
        userService.deleteAuthenticatedUser(token);

        return ResponseEntity.noContent().build();
    }

    //ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable Long id,
                                                          @Valid @RequestBody UserRequestDTO requestDTO) {

        return ResponseEntity.ok(userService.updateUserById(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
}

