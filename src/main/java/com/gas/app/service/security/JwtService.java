package com.gas.app.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gas.app.entity.security.token.Token;
import com.gas.app.entity.security.token.TokenType;
import com.gas.app.entity.security.user.User;
import com.gas.app.repository.security.token.TokenRepository;
import com.gas.app.repository.security.user.UserRepository;
import com.gas.app.service.security.cookieService.CookieService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token.expiration}")
    private Long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    private final CookieService cookieService;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;



    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        return buildToken(extraClaims,userDetails,jwtExpiration);
    }
    public String generateRefreshToken(
            UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }
    public String buildToken(Map<String, Object> extraClaims,
                             UserDetails userDetails,
                             Long expiration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @Transactional
    public String refreshToken(
            HttpServletRequest request
    )  {
        final String refreshToken;
        final String userEmail;
        refreshToken = cookieService.extractCookie(request.getCookies(), "refresh_token");
        userEmail = extractUsername(refreshToken);

        if(userEmail != null ){

            User user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();

            if(isTokenValid(refreshToken, user)){
                String accessToken = generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                return accessToken;
            }
        }
        return null;
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

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

    public boolean isAccessTokenExpired(String token, Cookie[] cookie) {
        DecodedJWT jwtDecoded = JWT.decode(token);
        return jwtDecoded.getExpiresAt().before(new Date());
    }

    public boolean isRefreshTokenExpired(Cookie[] cookie) {
        String refreshToken = cookieService.extractCookie(cookie, "refresh_token");
        DecodedJWT refreshTokenDecoded = JWT.decode(refreshToken);
        return refreshTokenDecoded.getExpiresAt().before(new Date());

    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
