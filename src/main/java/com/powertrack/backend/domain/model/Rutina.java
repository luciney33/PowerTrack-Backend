package com.powertrack.backend.domain.model;

import java.util.List;

public record Rutina(
        Long id,
        String nombre,
        String descripcion,
        Integer tipo,
        List<Ejercicio> ejercicios
) {}