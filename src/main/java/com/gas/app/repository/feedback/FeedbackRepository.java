package com.gas.app.repository.feedback;

import com.gas.app.entity.general.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {


}