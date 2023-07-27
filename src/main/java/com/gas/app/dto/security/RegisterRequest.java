package com.gas.app.dto.security;

public record RegisterRequest(String firstName, String lastName, String gasAccountNumber, String email, String password) {
}
