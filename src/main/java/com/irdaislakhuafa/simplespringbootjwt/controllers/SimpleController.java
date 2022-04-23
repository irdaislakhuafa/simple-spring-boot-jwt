package com.irdaislakhuafa.simplespringbootjwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/hello" })
public class SimpleController {
    @GetMapping(value = { "/user" })
    public ResponseEntity<?> helloUser() {
        return ResponseEntity.ok(null);
    }
}