package com.powertrack.backend.domain.model;

public record Comida(
        Long id,
        String nombre,
        Integer calorias,
        Double proteinas,
        Double carbohidratos,
        Double grasas,
        String categoria
) {
}
