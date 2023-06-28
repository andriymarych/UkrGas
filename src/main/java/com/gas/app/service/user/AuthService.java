package com.gas.app.service.user;


import com.gas.app.exception.ServiceException;
import com.gas.app.dto.user.UserRegistrationDto;
import com.gas.app.dto.user.UserSessionDto;
import com.gas.app.entity.user.Auth;
import com.gas.app.entity.user.User;
import com.gas.app.repository.user.AuthRepository;
import com.gas.app.repository.user.UserRepository;
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


    @Transactional(readOnly = true)
    public boolean isEmailAlreadyInUse(String email) {
        return authRepository.findAuthByEmail(email)
                .isPresent();
    }

    public Auth generateAuth(UserRegistrationDto userForm) {
        String encodedPassword = passwordEncoder.encode(userForm.password());
        return new Auth(userForm.email(), encodedPassword);
    }

    @Transactional(readOnly = true)
    public void verifyAuth(UserSessionDto userSessionDto) {

        Long userId = userSessionDto.userId();
        Long authId = userSessionDto.authId();

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new ServiceException("User [" + userId + "] is not found", HttpStatus.NOT_FOUND));

        if (!user.getAuth().getId().equals(authId)) {
            throw new ServiceException("User [" + userId + "] entered an incorrect auth id", HttpStatus.FORBIDDEN);
        }

    }
}
