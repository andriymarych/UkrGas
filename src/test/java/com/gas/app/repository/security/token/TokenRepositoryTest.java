package com.gas.app.repository.security.token;

import com.gas.app.config.PostgreSQLTestContainerConfiguration;
import com.gas.app.entity.security.token.Token;
import com.gas.app.entity.security.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PostgreSQLTestContainerConfiguration.class)
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    @Transactional
    void shouldReturnAllValidTokensByUser() {
        User user = new User();

        Token firstValidToken = new Token();
        firstValidToken.setUser(user);
        firstValidToken.setRevoked(false);
        firstValidToken.setExpired(false);

        Token secondValidToken = new Token();
        secondValidToken.setRevoked(false);
        secondValidToken.setExpired(false);
        secondValidToken.setUser(user);

        Token invalidToken = new Token();
        invalidToken.setRevoked(true);
        invalidToken.setExpired(true);
        invalidToken.setUser(user);
        tokenRepository.saveAll(List.of(firstValidToken, secondValidToken, invalidToken));

        List<Token> expectedValidTokens = List.of(firstValidToken, secondValidToken);

        List<Token> foundValidTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        assertThat(foundValidTokens).hasSameElementsAs(expectedValidTokens);


    }

    @Test
    @Transactional
    void shouldReturnTokenByTokenValue() {
        User user = new User();
        String tokenValue = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFt";
        Token token = new Token();
        token.setToken(tokenValue);
        token.setUser(user);

        tokenRepository.save(token);

        Optional<Token> foundToken = tokenRepository.findByToken(tokenValue);

        assertThat(foundToken.isPresent()).isTrue();
        assertThat(foundToken.get()).isEqualTo(token);

    }
}