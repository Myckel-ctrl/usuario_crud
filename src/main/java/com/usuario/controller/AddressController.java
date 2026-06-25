package com.usuario.controller;

import com.usuario.dto.address.AddressRequestDTO;
import com.usuario.dto.address.AddressResponseDTO;
import com.usuario.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestHeader("Authorization") String token,
                                                     @Valid @RequestBody AddressRequestDTO request) {

        return ResponseEntity.ok(addressService.createAddress(token, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String token,
                                                     @Valid @RequestBody AddressRequestDTO request) {

        return ResponseEntity.ok(addressService.updateAddress(id, token, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id,
                                       @RequestHeader("Authorization") String token) {

        addressService.deleteAddress(id, token);

        return ResponseEntity.noContent().build();
    }
}
