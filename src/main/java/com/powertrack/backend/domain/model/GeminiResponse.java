package com.powertrack.backend.domain.model;

import java.util.List;

public record GeminiResponse(List<Candidate> candidates) {
    public record Candidate(GeminiContent content) {}
    public record GeminiContent(List<GeminiPart> parts) {}
    public record GeminiPart(String text) {}
}