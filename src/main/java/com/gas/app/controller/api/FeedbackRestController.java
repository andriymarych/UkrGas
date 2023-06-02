package com.gas.app.controller.api;

import com.gas.app.dto.FeedbackDto;
import com.gas.app.entity.Feedback;
import com.gas.app.service.FeedbackService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackRestController {

    private final FeedbackService service;
    @PostMapping("/create")
    public ResponseEntity<Object> saveBooking(@RequestBody FeedbackDto feedbackDto) {
        Feedback feedback = service.saveFeedback(feedbackDto);
        return ResponseHandler.generateResponse("Feedback was successfully created", HttpStatus.CREATED, feedback);
    }
}