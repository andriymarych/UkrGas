package com.gas.app.repository;

import com.gas.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select distinct u from User u " +
            "left join fetch u.auth")
    User findUserByEmail(String email);

    @Query("select distinct u from User u where u.id = :id")
    User findByIdCustom(Long id);
}
