package com.amal.sunbase.Service.Impl;

import com.amal.sunbase.Dto.Request.CreateUserDto;
import com.amal.sunbase.Dto.Request.LoginDto;
import com.amal.sunbase.Dto.Response.LoginResponse;
import com.amal.sunbase.Exception.EmailAlreadyExistException;
import com.amal.sunbase.Exception.InvalidCredentialException;
import com.amal.sunbase.Model.User;
import com.amal.sunbase.Repository.UserRepository;
import com.amal.sunbase.Security.JwtUtils;
import com.amal.sunbase.Security.UserSecurityDetails;
import com.amal.sunbase.Service.Interface.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service class responsible for authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    //Injecting dependencies with the help of @Autowired
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private UserSecurityDetails userSecurityDetails;


    /**
     * Authenticates a user based on the provided login credentials.
     * Generates a JWT token upon successful authentication.
     *
     * @param loginDto - DTO containing login credentials
     * @return - LoginResponse containing user details and JWT token
     * @throws InvalidCredentialException - Thrown if the provided email is invalid or authentication fails
     */
    @Override
    public LoginResponse login(LoginDto loginDto)throws Exception{
        Authentication authentication = null;

        //validate user credential with authenticationManager
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword()));
        } catch (Exception e) {
            throw new InvalidCredentialException(); // Throw exception if authentication fails
        }

        //fetch user using email
        User user = userRepository.findByEmail(loginDto.getLoginId())
                .orElseThrow(() -> new InvalidCredentialException());

        // Sets the authenticated user in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generates a JWT token for the authenticated user
        String jwt = jwtUtils.generateJwtTokenForUser(user);

        // Constructs a new LoginResponse  with user details and JWT token
        LoginResponse response = LoginResponse.builder().
                email(user.getUsername()).
                jwtToken(jwt).build();

        return response; // Returns the LoginResponse
    }

    /**
     * Registers a new user in the system.
     * Checks if the user with the provided email already exists in the database.
     * If the email is unique, creates a new user and saves it along with the associated address.
     * Both Admin and User can create account
     *
     * @param userDto - DTO containing user details for registration
     * @throws EmailAlreadyExistException - Thrown if the provided email already exists in the database
     */
    @Override
    public void signup(CreateUserDto userDto)throws Exception {

        // Checks if a user with the provided email already exists in the database then it throw exception
        Optional<User> sameEmail= userRepository.findByEmail(userDto.getEmail());
        if(sameEmail.isPresent()){
            throw new EmailAlreadyExistException();
        }

        // Constructs a new user object from the userDto
        User user=User.builder().
                name(userDto.getName()).
                email(userDto.getEmail()).
                password(userDto.getPassword()).
                build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

}