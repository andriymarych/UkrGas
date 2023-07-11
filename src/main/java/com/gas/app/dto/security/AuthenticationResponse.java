package com.gas.app.dto.security;

public record AuthenticationResponse(String accessToken, String refreshToken) {
}
