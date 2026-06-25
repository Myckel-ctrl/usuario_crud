package com.usuario.dto.phone;

import com.usuario.entity.enums.PhoneType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneResponseDTO {
    private Long id;
    private String number;
    private PhoneType type;
}