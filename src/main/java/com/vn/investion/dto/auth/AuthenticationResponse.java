package com.vn.investion.dto.auth;

public record AuthenticationResponse(String token, UserResponse user) {
}
