package com.gas.app.service.security.cookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CookieService {

    @Value("${application.security.jwt.access-token.expiration}")
    private Long jwtExpiration;


    public String extractCookie(Cookie[] cookies, String cookieName) {

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();

                }
            }
        }
        return null;
    }

    public void clearSessionCookies(@NonNull HttpServletResponse response) {
        List<String> cookies = List.of("access_token", "refresh_token", "is_user_authorized");
        cookies.forEach( cookie -> {
                    deleteCookie(response, cookie);
                }
        );
    }

    public void addAccessToken(@NonNull HttpServletResponse response,
                               String name, String value) {
        Cookie accessTokenCookie = new Cookie(name, value);
        accessTokenCookie.setAttribute("SameSite","Lax");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setMaxAge(Math.toIntExact(jwtExpiration));
        accessTokenCookie.setPath("/");
        accessTokenCookie.setDomain("localhost");
        response.addCookie(accessTokenCookie);
    }

    public void deleteCookie(@NonNull HttpServletResponse response,
                          String name) {
        Cookie accessTokenCookie = new Cookie(name, null);
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);
    }
}