package com.gas.app.repository;

import com.gas.app.entity.Feedback;
import com.gas.app.entity.PersonalGasAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {


}