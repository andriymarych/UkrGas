package com.gas.app.service;



import com.gas.app.exception.ServiceException;
import com.gas.app.dto.UserRegistrationDto;
import com.gas.app.entity.Auth;
import com.gas.app.entity.PersonalGasAccount;
import com.gas.app.entity.User;
import com.gas.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PersonalGasAccountService personalGasAccountService;

    @Transactional
    public User registerUser(UserRegistrationDto userForm) {
        if (authService.isEmailAlreadyInUse(userForm.email())) {
            throw new ServiceException("Email [" + userForm.email() + "] is already in use",
                    HttpStatus.CONFLICT);
        }
        PersonalGasAccount personalGasAccount = personalGasAccountService
                .getAccountByAccountNumber(userForm.gasAccountNumber());
        Auth userAuth = authService.generateAuth(userForm);
        User user = new User();
        user.setAuth(userAuth);
        personalGasAccount.setUser(user);
        return userRepository.save(user);
    }


    @Transactional
    public User authenticateUser(String email, String password) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new ServiceException("User with email [" + email + "] is not found ", HttpStatus.UNAUTHORIZED));
        if (passwordEncoder.matches(password, user.getAuth().getPassword())) {
            return user;
        }
        throw new ServiceException("User [" + email + "] entered an incorrect password",HttpStatus.UNAUTHORIZED);
    }
}
