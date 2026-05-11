package com.powertrack.backend.domain.model;

public record RutinaEjercicio(
        Long ejercicioId,
        String nombre,
        String tipoEntrenamiento,
        String imagenUrl,
        String descripcion,
        Integer series,
        Integer repeticiones,
        Integer descansoSeg
) {}