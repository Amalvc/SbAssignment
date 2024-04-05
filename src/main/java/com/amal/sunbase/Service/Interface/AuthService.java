package com.amal.sunbase.Service.Interface;

import com.amal.sunbase.Dto.Request.CreateUserDto;
import com.amal.sunbase.Dto.Request.LoginDto;
import com.amal.sunbase.Dto.Response.LoginResponse;

/**
 * Interface defining authentication service operations.
 */
public interface AuthService {

    /**
     * Authenticates a user based on provided login credentials.
     *
     * @param loginDto - DTO containing login credentials
     * @return - LoginResponse containing user details and JWT token upon successful authentication
     */
    LoginResponse login(LoginDto loginDto)throws Exception;

    /**
     * Registers a new user.
     *
     * @param createUserDto - DTO containing user details for registration
     */
    void signup(CreateUserDto createUserDto) throws Exception;
}