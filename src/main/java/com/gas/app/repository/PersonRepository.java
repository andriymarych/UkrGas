package com.gas.app.repository;


import com.gas.app.entity.Person;
import com.gas.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select distinct p from Person p where p.id = :personId")
    Optional<Person> findUserById(Long personId);

}
