package com.gas.app.service.security;


import com.gas.app.dto.security.AuthenticationRequest;
import com.gas.app.dto.security.AuthenticationResponse;
import com.gas.app.dto.security.RegisterRequest;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.security.user.Role;
import com.gas.app.entity.security.user.User;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.security.user.UserRepository;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PersonalGasAccountService personalGasAccountService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.findUserByEmail(request.email()).isPresent()) {
            throw new ServiceException("Email [" + request.email() + "] is already in use",
                    HttpStatus.CONFLICT);
        }

        PersonalGasAccount personalGasAccount = personalGasAccountService
                .getAccountByAccountNumber(request.gasAccountNumber());

        User user = User.builder()
                .firstName(personalGasAccount.getPerson().getFirstName())
                .lastName(personalGasAccount.getPerson().getLastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        personalGasAccount.setUser(user);
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        jwtService.saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try{authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password())
        );
        }catch (BadCredentialsException e){
            throw new ServiceException("User entered incorrect credentials",HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(
                        () -> new ServiceException("User with email [" +
                                request.email() + "] is not found ",
                                HttpStatus.UNAUTHORIZED));
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        jwtService.revokeAllUserTokens(user);
        jwtService.saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public User getCurrentUser(){
        User currentUser = (User)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentUser != null){
            return currentUser;
        }else{
            throw new ServiceException("User is not authorized ",
                    HttpStatus.UNAUTHORIZED);
        }
    }
    @Transactional
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        jwtService.refreshToken(request);
    }
}
