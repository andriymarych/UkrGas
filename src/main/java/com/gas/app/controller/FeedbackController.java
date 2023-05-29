package com.gas.app.controller;


import com.gas.app.controller.exception.ServiceException;
import com.gas.app.dto.FeedbackDto;
import com.gas.app.entity.Feedback;
import com.gas.app.service.FeedbackService;
import com.gas.app.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService service;
    @GetMapping
    public String getPage() {
        return "feedback";
    }

    @PostMapping
    public ResponseEntity<Object> saveBooking(@RequestBody FeedbackDto feedbackDto) {
        Feedback feedback = service.saveFeedback(feedbackDto).orElseThrow(
                () -> new ServiceException("Feedback creation error", HttpStatus.CONFLICT)
        );
        return ResponseHandler.generateResponse("Booking was successfully made", HttpStatus.OK, feedback);
    }
}
