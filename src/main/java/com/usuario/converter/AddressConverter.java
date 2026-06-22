package com.usuario.converter;

import com.usuario.dto.address.AddressRequestDTO;
import com.usuario.dto.address.AddressResponseDTO;
import com.usuario.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public Address toAddressEntity(AddressRequestDTO dto) {
        return Address.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .build();
    }

    public AddressResponseDTO toAddressDTO(Address address) {
        return AddressResponseDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }
}
