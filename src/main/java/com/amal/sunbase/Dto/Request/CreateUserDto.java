package com.amal.sunbase.Dto.Request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
