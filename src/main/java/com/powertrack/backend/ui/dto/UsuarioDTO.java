package com.powertrack.backend.ui.dto;

public record UsuarioDTO(
        String username,
        String password,
        String email,
        String nombre
) {}
