package com.gas.app.filter;

import com.gas.app.repository.security.token.TokenRepository;
import com.gas.app.service.security.JwtService;
import com.gas.app.service.security.cookieService.CookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CookieService cookieService;

    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = cookieService.extractCookie(request.getCookies(), "access_token");
        final String userEmail;
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = verifyAccessAndRefreshTokens(jwt, request,response);
        if(jwt == null){
            return;
        }
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            boolean isTokenValid = tokenRepository.findByToken(jwt)
                    .map(token -> !token.getExpired() && !token.getRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String verifyAccessAndRefreshTokens(String jwt,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        if (jwtService.isRefreshTokenExpired(request.getCookies())) {
            logout(response);
            return null;
        }

        if (jwtService.isAccessTokenExpired(jwt, request.getCookies()) &&
                !jwtService.isRefreshTokenExpired(request.getCookies())) {

            jwt = jwtService.refreshToken(request);
            cookieService.addAccessToken(response, "access_token", jwt);
        }
        return jwt;
    }

    private void logout(HttpServletResponse response) throws IOException {
        WebClient.create("https://localhost/api/v2/auth/logout")
                .get()
                .retrieve();
        cookieService.clearSessionCookies(response);
        response.sendRedirect("https://localhost:8443/");
    }
}