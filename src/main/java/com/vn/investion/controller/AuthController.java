package com.vn.investion.controller;

import com.vn.investion.dto.auth.AuthenticationRequest;
import com.vn.investion.dto.auth.AuthenticationResponse;
import com.vn.investion.dto.auth.RegisterRequest;
import com.vn.investion.dto.Response;
import com.vn.investion.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthController(AuthenticationService authenticationService) {


    @PostMapping("/register")
    public Response<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return Response.ofSucceeded(authenticationService.register(request));
    }

    @PostMapping("/login")
    public Response<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return Response.ofSucceeded(authenticationService.authenticate(request));
    }
}
