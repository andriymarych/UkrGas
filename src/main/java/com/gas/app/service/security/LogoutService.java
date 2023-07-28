package com.gas.app.service.security;

import com.gas.app.entity.security.token.Token;
import com.gas.app.repository.security.token.TokenRepository;
import com.gas.app.service.security.cookieService.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final CookieService cookieService;
    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String jwt = cookieService.extractCookie(request.getCookies(), "access_token");

        if(jwt == null){
            return;
        }
        Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if(storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
        cookieService.clearSessionCookies(response);
    }
}
