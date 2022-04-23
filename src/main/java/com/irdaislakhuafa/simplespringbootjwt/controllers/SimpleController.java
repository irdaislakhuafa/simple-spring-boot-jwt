package com.irdaislakhuafa.simplespringbootjwt.controllers;

import java.util.HashMap;

import com.irdaislakhuafa.simplespringbootjwt.utils.api.ApiMessage;
import com.irdaislakhuafa.simplespringbootjwt.utils.api.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/hello" })
public class SimpleController {
    @GetMapping(value = { "/user" })
    public ResponseEntity<?> helloUser() {
        ApiResponse<Object> response = ApiResponse.builder()
                .message(ApiMessage.SUCCESS)
                .data(new HashMap<>() {
                    {
                        put("message", "Hello User");
                    }
                })
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = { "/admin" })
    public ResponseEntity<?> helloAdmin() {
        ApiResponse<Object> response = ApiResponse.builder()
                .message(ApiMessage.SUCCESS)
                .data(new HashMap<>() {
                    {
                        put("message", "Hello Admin");
                    }
                })
                .build();
        return ResponseEntity.ok().body(response);
    }
}