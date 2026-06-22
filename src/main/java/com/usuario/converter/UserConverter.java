package com.usuario.converter;

import com.usuario.dto.user.UserRequestDTO;
import com.usuario.dto.user.UserResponseDTO;
import com.usuario.entity.User;
import com.usuario.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final AddressConverter addressConverter;
    private final PhoneConverter phoneConverter;

    public User toUserEntity(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public UserResponseDTO toUserDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .addresses(user.getAddresses() == null ? null :
                        user.getAddresses().stream()
                                .map(addressConverter::toAddressDTO)
                                .toList())
                .phones(user.getPhones() == null ? null :
                        user.getPhones().stream()
                                .map(phoneConverter::toPhoneDTO)
                                .toList())
                .build();
    }
}
