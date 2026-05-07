package com.powertrack.backend.ui.dto;

public record RegistroDetalleResponseDTO(
        Long id,
        EjercicioResponseDTO ejercicio,
        Integer series,
        Integer repeticiones,
        Double peso,
        Integer duracionMinutos,
        Double velocidad,
        Double inclinacion,
        Integer kcalGastadas
) {}
