package com.powertrack.backend.ui.dto;


public record RefreshTokenRequest(
        String refreshToken,
        String accessToken
) {
    }

