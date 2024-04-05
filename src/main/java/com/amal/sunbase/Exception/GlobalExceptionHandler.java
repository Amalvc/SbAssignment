package com.amal.sunbase.Exception;
import com.amal.sunbase.Dto.Response.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * It provides centralized exception handling for various exceptions that may occur during
 * the execution of controller methods.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles the exception when a user already exists in the system.
     */
    @ExceptionHandler({EmailAlreadyExistException.class})
    public ResponseEntity handleEmailAlreadyRegisteredException() {
        log.error("Email already registered");

        CommonResponseDto responseDTO = new CommonResponseDto(false, 409, "Email address provided is already registered with an account", null);
        ResponseEntity responseEntity = new ResponseEntity(responseDTO, HttpStatus.CONFLICT);
        return responseEntity;
    }

    @ExceptionHandler({PhoneNumberAlreadyExistException.class})
    public ResponseEntity handlePhoneNumberAlreadyRegisteredException() {
        log.error("Email already registered");

        CommonResponseDto responseDTO = new CommonResponseDto(false, 409, "Phone number provided is already registered with an account", null);
        ResponseEntity responseEntity = new ResponseEntity(responseDTO, HttpStatus.CONFLICT);
        return responseEntity;
    }

    /**
     * Handles the exception when invalid credentials are provided during authentication.
     */
    @ExceptionHandler({InvalidCredentialException.class})
    public ResponseEntity handleInvalidCredentialException() {
        log.error("Email or password is wrong");

        CommonResponseDto responseDTO = new CommonResponseDto(false, 401, "Email or password is wrong", null);
        ResponseEntity responseEntity = new ResponseEntity(responseDTO, HttpStatus.UNAUTHORIZED);
        return responseEntity;
    }

    /**
     * Handles the exception when a user is not found.
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity handleUserNotFoundException() {
        log.error("Item not found");

        CommonResponseDto responseDTO = new CommonResponseDto(false, 404, "User not found", null);
        ResponseEntity responseEntity = new ResponseEntity(responseDTO, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

}
