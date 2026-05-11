package com.powertrack.backend.ui.dto;

public record ComidaResponseDTO(
        Long id,
        String nombre,
        Integer calorias,
        Double proteinas,
        Double carbohidratos,
        Double grasas,
        String categoria,
        String imagenUrl
) {}
