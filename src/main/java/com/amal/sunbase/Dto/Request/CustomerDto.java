package com.amal.sunbase.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
    private String phone;

}

