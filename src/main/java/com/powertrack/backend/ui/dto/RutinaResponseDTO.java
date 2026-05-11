package com.powertrack.backend.ui.dto;

import java.util.List;

public record RutinaResponseDTO(Long id,
                                String nombre,
                                String descripcion,
                                Integer tipo,
                                List<RutinaEjercicioResponseDTO> ejercicios
) {
}
