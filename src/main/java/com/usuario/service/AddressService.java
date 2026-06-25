package com.usuario.service;

import com.usuario.converter.AddressConverter;
import com.usuario.dto.address.AddressRequestDTO;
import com.usuario.dto.address.AddressResponseDTO;
import com.usuario.entity.Address;
import com.usuario.entity.User;
import com.usuario.exceptions.AccessDeniedException;
import com.usuario.exceptions.ResourceNotFoundException;
import com.usuario.repository.AddressRepository;
import com.usuario.repository.UserRepository;
import com.usuario.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressConverter addressConverter;
    private final JwtUtil jwtUtil;

    @Transactional
    public AddressResponseDTO createAddress(String token, AddressRequestDTO request) {
        User user = getAuthenticatedUser(token);

        Address address = addressConverter.toAddressEntity(request);

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return addressConverter.toAddressDTO(savedAddress);
    }

    @Transactional
    public AddressResponseDTO updateAddress(Long addressId, String token, AddressRequestDTO requestDTO) {
        User authenticatedUser = getAuthenticatedUser(token);

        Address address = findAddressById(addressId);

        validateAddressOwner(address, authenticatedUser);

        address.setStreet(requestDTO.getStreet() != null ? requestDTO.getStreet() : address.getStreet());
        address.setNumber(requestDTO.getNumber() != null ? requestDTO.getNumber() : address.getNumber());
        address.setComplement(requestDTO.getComplement() != null ? requestDTO.getComplement() : address.getComplement());
        address.setCity(requestDTO.getCity() != null ? requestDTO.getCity() : address.getCity());
        address.setState(requestDTO.getState() != null ? requestDTO.getState() : address.getState());
        address.setZipcode(requestDTO.getZipcode() != null ? requestDTO.getZipcode() : address.getZipcode());
        return addressConverter.toAddressDTO(addressRepository.save(address));
    }

    @Transactional
    public void deleteAddress(Long addressId, String token) {
        User authenticatedUser = getAuthenticatedUser(token);

        Address address = findAddressById(addressId);

        validateAddressOwner(address, authenticatedUser);

        addressRepository.delete(address);
    }

    //MÉTODOS PRIVADOS
    private User getAuthenticatedUser(String token) {
        String email = jwtUtil.extractUsername(token.substring(7));

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found."));
    }

    private void validateAddressOwner(Address address, User authenticatedUser) {
        if (!address.getUser().getId().equals(authenticatedUser.getId())) {
            throw new AccessDeniedException("You do not have permission to change this address.");
        }
    }
}

