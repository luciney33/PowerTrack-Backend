package com.powertrack.backend.domain.model;

import java.util.List;

public record GeminiRequest(List<GeminiContent> contents) {
    public record GeminiContent(List<GeminiPart> parts) {}
    public record GeminiPart(String text) {}
}
