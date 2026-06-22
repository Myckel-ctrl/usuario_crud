package com.usuario.converter;

import com.usuario.dto.phone.PhoneRequestDTO;
import com.usuario.dto.phone.PhoneResponseDTO;
import com.usuario.entity.Phone;
import org.springframework.stereotype.Component;

@Component
public class PhoneConverter {

    public Phone toPhoneEntity(PhoneRequestDTO dto) {
        return Phone.builder()
                .number(dto.getNumber())
                .type(dto.getType())
                .build();
    }

    public PhoneResponseDTO toPhoneDTO(Phone phone) {
        return PhoneResponseDTO.builder()
                .id(phone.getId())
                .number(phone.getNumber())
                .type(phone.getType())
                .build();
    }
}