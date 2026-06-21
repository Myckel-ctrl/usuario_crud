package com.usuario.dto.user;

import com.usuario.dto.address.AddressResponseDTO;
import com.usuario.dto.phone.PhoneResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private List<AddressResponseDTO> addresses;
    private List<PhoneResponseDTO> phones;
}