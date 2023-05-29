package com.gas.app.service;


import com.gas.app.dto.UserRegistrationForm;
import com.gas.app.entity.Auth;
import com.gas.app.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public boolean isEmailAlreadyInUse(String email) {
        return authRepository.findAuthByEmail(email)
                .isPresent();
    }
    @Transactional
    public Auth generateAuth(UserRegistrationForm userForm) {
        String encodedPassword = passwordEncoder.encode(userForm.password());
        return new Auth(userForm.email(), encodedPassword);
    }
}
