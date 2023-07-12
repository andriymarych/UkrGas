package com.gas.app.service.security.cookieService;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {


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

}