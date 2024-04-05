package com.amal.sunbase.Dto.Request;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerDto {

    @NonNull
    private String email;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String address;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    @Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
    private String phone;
}

