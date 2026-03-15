package com.powertrack.backend.ui.dto;

import java.util.List;

public record PlanNutricionalResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        Integer tipo,
        Integer caloriasObjetivo,
        Double proteinasObjetivo,
        Double carbohidratosObjetivo,
        Double grasasObjetivo,
        List<ComidaResponseDTO> comidas
) {}
