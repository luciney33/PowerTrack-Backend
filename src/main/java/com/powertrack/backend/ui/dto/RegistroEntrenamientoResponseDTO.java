package com.powertrack.backend.ui.dto;

import java.time.LocalDate;
import java.util.List;

public record RegistroEntrenamientoResponseDTO(
        Long id,
        Long rutinaId,
        LocalDate fecha,
        String observaciones,
        List<RegistroDetalleResponseDTO> detalles
) {}
