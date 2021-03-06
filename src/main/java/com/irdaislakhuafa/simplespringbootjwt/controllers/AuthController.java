package com.irdaislakhuafa.simplespringbootjwt.controllers;

import java.util.Optional;

import com.irdaislakhuafa.simplespringbootjwt.model.dtos.UserDto;
import com.irdaislakhuafa.simplespringbootjwt.model.dtos.requests.AuthRequest;
import com.irdaislakhuafa.simplespringbootjwt.model.dtos.requests.AuthResponse;
import com.irdaislakhuafa.simplespringbootjwt.model.entities.User;
import com.irdaislakhuafa.simplespringbootjwt.services.UserService;
import com.irdaislakhuafa.simplespringbootjwt.utils.api.ApiMessage;
import com.irdaislakhuafa.simplespringbootjwt.utils.api.ApiResponse;
import com.irdaislakhuafa.simplespringbootjwt.utils.jwt.JwtUtility;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/auth" })
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtility jwtUtility;

    @Qualifier(value = "authenticationManager")
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = { "/register" })
    public ResponseEntity<?> register(@RequestBody(required = true) UserDto userDto) {
        Optional<User> savedUser = Optional.empty();
        ResponseEntity<?> response = null;
        try {
            savedUser = userService.save(userService.mapToEntity(userDto));
            response = ResponseEntity.ok(ApiResponse.builder()
                    .message(ApiMessage.SUCCESS)
                    .data(savedUser)
                    .build());
            log.info("Success register new user");
        } catch (Exception e) {
            log.error("Error: " + e.getCause().getMessage());
            response = ResponseEntity.internalServerError()
                    .body(ApiResponse.builder()
                            .message(ApiMessage.ERROR)
                            .error(e.getCause().getMessage())
                            .build());
            log.info("Failed register new user");
        }
        return response;
    }

    @PostMapping(value = { "/login", "/", "/authenticate" })
    public ResponseEntity<?> authentication(@RequestBody(required = true) AuthRequest authRequest) {
        log.info("Preparing authentication");
        AuthResponse authResponse = null;
        ResponseEntity<?> responses = null;

        try {
            log.info("Preparing username and password authentication token");
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword());
            log.info("Username and password authentication token is created");

            log.info("Preparing authenticated");
            authenticationManager.authenticate(auth);
            log.info("Success authenticated user");

            log.info("Getting user information");
            final Optional<User> authUser = userService.findByEmail(authRequest.getEmail());

            log.info("Generate token");
            final String token = jwtUtility.generateJwtToken(authUser.get());

            log.info("Preparing auth response");
            authResponse = AuthResponse.builder()
                    .message(ApiMessage.SUCCESS)
                    .error(null)
                    .token(token)
                    .build();

            responses = ResponseEntity.ok(authResponse);
            log.info("Auth response is ready, authentication success");

        } catch (UsernameNotFoundException e) {
            log.error("Username or Email: " + authRequest.getEmail() + " doesn't exists");
            authResponse = AuthResponse.builder()
                    .message(ApiMessage.ERROR)
                    .error("Username or Email: " + authRequest.getEmail() + " doesn't exists")
                    .build();
            responses = new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);

        } catch (BadCredentialsException e) {
            log.error("Error: " + e.getMessage());
            authResponse = AuthResponse.builder()
                    .message(ApiMessage.ERROR)
                    .error("Incorrect username or password")
                    .token(null)
                    .build();
            responses = new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            authResponse = AuthResponse.builder()
                    .message(ApiMessage.ERROR)
                    .error(e.getMessage())
                    .build();
            responses = ResponseEntity.internalServerError().body(authResponse);
        }

        return responses;
    }
}
