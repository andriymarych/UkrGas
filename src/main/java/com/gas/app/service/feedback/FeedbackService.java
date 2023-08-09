package com.gas.app.service.feedback;

import com.gas.app.dto.feedback.FeedbackDto;
import com.gas.app.entity.general.Feedback;
import com.gas.app.repository.general.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    @Transactional
    public Feedback saveFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback(feedbackDto.fullName(),
                feedbackDto.email(),
                feedbackDto.content());
        feedback.setTypeId(feedbackDto.feedbackType());
        feedback.setCategoryId(feedbackDto.feedbackCategory());
        return feedbackRepository.save(feedback);
    }
}
