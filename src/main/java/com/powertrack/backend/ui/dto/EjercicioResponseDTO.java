package com.powertrack.backend.ui.dto;

public record EjercicioResponseDTO(
        Long id,
        String nombre,
        String tipoEntrenamiento,
        String imagenUrl,
        String descripcion
) {}
