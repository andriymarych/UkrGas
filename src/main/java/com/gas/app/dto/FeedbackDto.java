package com.gas.app.dto;

public record FeedbackDto(String fullName, String email, Integer feedbackType,
                          Integer feedbackCategory, String content) {
}