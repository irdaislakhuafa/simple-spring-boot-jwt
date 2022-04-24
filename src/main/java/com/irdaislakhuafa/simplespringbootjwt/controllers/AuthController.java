package com.irdaislakhuafa.simplespringbootjwt.controllers;

import java.util.Optional;

import com.irdaislakhuafa.simplespringbootjwt.model.dtos.UserDto;
import com.irdaislakhuafa.simplespringbootjwt.model.dtos.requests.AuthRequest;
import com.irdaislakhuafa.simplespringbootjwt.model.entities.User;
import com.irdaislakhuafa.simplespringbootjwt.services.UserService;
import com.irdaislakhuafa.simplespringbootjwt.utils.api.ApiMessage;
import com.irdaislakhuafa.simplespringbootjwt.utils.api.ApiResponse;
import com.irdaislakhuafa.simplespringbootjwt.utils.jwt.JwtUtility;

import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = { "/register" })
    public ResponseEntity<?> register(@RequestBody(required = true) UserDto userDto) {
        log.info("oioi");
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
            log.error("Error: ", e);
            response = ResponseEntity.internalServerError()
                    .body(ApiResponse.builder()
                            .message(ApiMessage.ERROR)
                            .error(e.getMessage())
                            .build());
            log.info("Failed register new user");
        }
        return response;
    }

    @PostMapping(value = { "/login", "/" })
    public ResponseEntity<?> login(@RequestBody(required = true) AuthRequest authRequest) {
        // TODO : add authentication task
        return ResponseEntity.ok().body(authRequest);
    }
}
