package com.amal.sunbase.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseDto {
    private boolean status;
    private int code;
    private String message;
    private Object data;
}
