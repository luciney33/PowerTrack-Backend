package com.powertrack.backend.ui.dto;


public record LoginResponse(
        boolean success,
        String message,
        String accessToken,
        String refreshToken,
        String tokenType,
        UsuarioResponseDTO usuario
) {
    public LoginResponse(String accessToken, String refreshToken,
                         UsuarioResponseDTO usuario, String message) {
        this(true, message, accessToken, refreshToken, "Bearer", usuario);
    }
}

