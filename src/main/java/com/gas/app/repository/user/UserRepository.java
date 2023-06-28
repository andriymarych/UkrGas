package com.gas.app.repository.user;

import com.gas.app.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select distinct user from User user " +
            "left join fetch user.auth auth " +
            "where auth.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("select distinct user from User user " +
            "left join fetch user.auth auth " +
            "where user.id = :id")
    Optional<User> findUserById(Long id);


}
