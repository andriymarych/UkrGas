package com.gas.app.service;

import com.gas.app.dto.FeedbackDto;
import com.gas.app.entity.Feedback;
import com.gas.app.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    @Transactional
    public Optional<Feedback> saveFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback(feedbackDto.fullName(),
                feedbackDto.email(),
                feedbackDto.content());
        feedback.setTypeId(feedbackDto.feedbackType());
        feedback.setCategoryId(feedbackDto.feedbackCategory());
        return Optional.of(feedbackRepository.save(feedback));
    }
}
