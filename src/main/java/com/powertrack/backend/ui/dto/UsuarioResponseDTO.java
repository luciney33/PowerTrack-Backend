package com.powertrack.backend.ui.dto;

import com.powertrack.backend.domain.model.Rol;

public record UsuarioResponseDTO(
        Long id,
        String username,
        String email,
        String nombre,
        Rol rol,
        boolean formularioCompletado,
        Integer recomendacion,
        String descripcionRutina,
        String consejosNutricion) {
}
