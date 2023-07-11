package com.gas.app.service.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gas.app.dto.security.AuthenticationRequest;
import com.gas.app.dto.security.AuthenticationResponse;
import com.gas.app.dto.security.RegisterRequest;
import com.gas.app.entity.personalAccount.PersonalGasAccount;
import com.gas.app.entity.security.token.Token;
import com.gas.app.entity.security.token.TokenType;
import com.gas.app.entity.security.user.Role;
import com.gas.app.entity.security.user.User;
import com.gas.app.exception.ServiceException;
import com.gas.app.repository.security.token.TokenRepository;
import com.gas.app.repository.security.user.UserRepository;
import com.gas.app.service.personalAccount.PersonalGasAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final PersonalGasAccountService personalGasAccountService;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
        saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password())
        );
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(
                        () -> new ServiceException("User with email [" +
                                request.email() + "] is not found ",
                                HttpStatus.UNAUTHORIZED));
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    @Transactional
    public void revokeAllUserTokens(User user){
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token->{
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    public void saveUserToken(User user, String jwtToken) {
        Token token  = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
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
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null ){

            User user = this.userRepository.findUserByEmail(userEmail)
                    .orElseThrow();

            if(jwtService.isTokenValid(refreshToken, user)){
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse =
                        new AuthenticationResponse(accessToken,refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
