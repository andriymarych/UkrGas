package com.gas.app.repository.user;

import com.gas.app.entity.user.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface AuthRepository extends JpaRepository<Auth, Long> {
    @Query("select  auth from Auth auth " +
            "where auth.email = :email")
    Optional<Auth> findAuthByEmail(String email);

}