package com.gas.app.repository.general;

import com.gas.app.entity.general.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {


}