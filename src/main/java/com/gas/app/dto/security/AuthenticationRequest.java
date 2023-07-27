package com.gas.app.dto.security;

public record AuthenticationRequest(String email, String password) {
}
