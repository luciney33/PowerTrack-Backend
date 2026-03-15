package com.powertrack.backend.domain.model;

import java.time.LocalDate;
import java.util.List;

public record RegistroEntrenamiento(
        Long id,
        Long usuarioId,
        Long rutinaId,
        LocalDate fecha,
        String observaciones,
        List<RegistroDetalle> detalles
) {}