package com.powertrack.backend.ui.dto;

public record LoginRequest(
        String username,
        String password
) {}
