package com.gas.app.controller.api.general;

import com.gas.app.dto.feedback.FeedbackDto;
import com.gas.app.entity.general.Feedback;
import com.gas.app.service.feedback.FeedbackService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/feedback")
@RequiredArgsConstructor
public class FeedbackRestController {

    private final FeedbackService service;
    @PostMapping
    public ResponseEntity<Object> saveBooking(@RequestBody FeedbackDto feedbackDto) {
        Feedback feedback = service.saveFeedback(feedbackDto);
        return ResponseHandler.generateResponse("Feedback was successfully created", HttpStatus.CREATED, feedback);
    }
}