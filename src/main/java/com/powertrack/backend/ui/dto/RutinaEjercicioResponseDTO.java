package com.powertrack.backend.ui.dto;

public record RutinaEjercicioResponseDTO(
        Long ejercicioId,
        String nombre,
        String tipoEntrenamiento,
        String imagenUrl,
        String descripcion,
        Integer series,
        Integer repeticiones,
        Integer descansoSeg
) {}