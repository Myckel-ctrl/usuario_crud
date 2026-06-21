package com.usuario.dto.phone;

import com.usuario.entity.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRequestDTO {

    @NotBlank
    private String number;
    @NotNull
    private PhoneType type;
}