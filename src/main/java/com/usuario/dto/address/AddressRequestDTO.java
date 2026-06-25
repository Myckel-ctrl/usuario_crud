package com.usuario.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {

    private String street;

    private String number;

    private String complement;

    private String city;

    @Size(min = 2, max = 2)
    private String state;

    @Size(min = 8, max = 9)
    private String zipcode;

}