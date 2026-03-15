package com.powertrack.backend.domain.model;

import java.util.List;

public record PlanNutricional(
        Long id,
        String nombre,
        String descripcion,
        Integer tipo,
        Integer caloriasObjetivo,
        Double proteinasObjetivo,
        Double carbohidratosObjetivo,
        Double grasasObjetivo,
        List<Comida> comidas
) { }
