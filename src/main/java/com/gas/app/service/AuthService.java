package com.gas.app.service;


import com.gas.app.controller.exception.ServiceException;
import com.gas.app.dto.UserRegistrationDto;
import com.gas.app.entity.Auth;
import com.gas.app.entity.User;
import com.gas.app.repository.AuthRepository;
import com.gas.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public boolean isEmailAlreadyInUse(String email) {
        return authRepository.findAuthByEmail(email)
                .isPresent();
    }
    @Transactional
    public Auth generateAuth(UserRegistrationDto userForm) {
        String encodedPassword = passwordEncoder.encode(userForm.password());
        return new Auth(userForm.email(), encodedPassword);
    }
    @Transactional
    public boolean verifyAuth(Long userId, Long authId){
        User user = userRepository.findUserById(userId)
                .orElseThrow( () -> new ServiceException("User with id[" + userId + "] is not found", HttpStatus.NOT_FOUND));
        return user.getAuth().getId().equals(authId);
    }
}
