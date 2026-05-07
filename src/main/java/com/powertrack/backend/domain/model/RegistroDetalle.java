package com.powertrack.backend.domain.model;

public record RegistroDetalle (
        Long id,
        Long registroId,
        Ejercicio ejercicio,
        Integer series,
        Integer repeticiones,
        Double peso,
        Integer duracionMinutos,
        Double velocidad,
        Double inclinacion,
        Integer kcalGastadas
) {}
