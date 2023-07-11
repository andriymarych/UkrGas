package com.gas.app.repository.security.token;

import com.gas.app.entity.security.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("select token " +
            "from Token token " +
            "inner join User user " +
            "where token.user.id  = :userId " +
            "and token.expired = false " +
            "or token.revoked = false")
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
