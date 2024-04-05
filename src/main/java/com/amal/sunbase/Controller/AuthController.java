package com.amal.sunbase.Controller;

import com.amal.sunbase.Dto.Request.CreateUserDto;
import com.amal.sunbase.Dto.Request.LoginDto;
import com.amal.sunbase.Dto.Response.CommonResponseDto;
import com.amal.sunbase.Dto.Response.LoginResponse;
import com.amal.sunbase.Service.Impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication-related APIs.
 * Maps endpoints for user signup, login
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    // Injecting auth service
    @Autowired
    private AuthServiceImpl authService;


    /**
     * Handles POST requests to "/api/auth/login" endpoint.
     * Authenticates user based on provided email and password.
     * Returns a response entity containing login response with JWT token.
     *
     * @param loginDto - DTO containing login credentials
     * @return - ResponseEntity containing login response with JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) throws Exception {
        log.info("Received login request.");

        LoginResponse response = authService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles POST requests to "/api/auth/signup" endpoint.
     * Creates a new user account based on provided details.
     * Returns a response entity indicating success or failure of the operation.
     *
     * @param createUserDto - DTO containing user details for signup
     * @return - ResponseEntity with success or failure message
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateUserDto createUserDto) throws Exception {
        log.info("Register new user.");


        authService.signup(createUserDto);
        CommonResponseDto result = new CommonResponseDto(true, 201, "Successfully created new account", null);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
