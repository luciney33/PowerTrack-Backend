package com.powertrack.backend.ui.dto;

import java.time.LocalDate;
import java.util.List;

public record RegistroEntrenamientoDTO(
        Long rutinaId,
        LocalDate fecha,
        String observaciones,
        List<RegistroDetalleDTO> detalles
) {}
