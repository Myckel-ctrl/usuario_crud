package com.usuario.controller;

import com.usuario.dto.phone.PhoneRequestDTO;
import com.usuario.dto.phone.PhoneResponseDTO;
import com.usuario.service.PhoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phones")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @PostMapping
    public ResponseEntity<PhoneResponseDTO> createPhone(@RequestHeader("Authorization") String token,
                                                   @Valid @RequestBody PhoneRequestDTO request) {

        return ResponseEntity.ok(phoneService.createPhone(token, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhoneResponseDTO> updatePhone(@PathVariable Long id,
                                                   @RequestHeader("Authorization") String token,
                                                   @Valid @RequestBody PhoneRequestDTO request) {

        return ResponseEntity.ok(phoneService.updatePhone(id, token, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id,
                                       @RequestHeader("Authorization") String token) {

        phoneService.deletePhone(id, token);

        return ResponseEntity.noContent().build();
    }
}
