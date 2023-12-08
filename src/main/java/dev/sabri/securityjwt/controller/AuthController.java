package dev.sabri.securityjwt.controller;

import dev.sabri.securityjwt.controller.dto.AuthenticationRequest;
import dev.sabri.securityjwt.controller.dto.AuthenticationResponse;
import dev.sabri.securityjwt.controller.dto.RegisterRequest;
import dev.sabri.securityjwt.model.Response;
import dev.sabri.securityjwt.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public record AuthController(AuthenticationService authenticationService) {


    @PostMapping("/register")
    public Response<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return Response.ofSucceeded(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
