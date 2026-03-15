package com.powertrack.backend.ui.dto;

public record RegistroDetalleDTO(
        Long ejercicioId,
        Integer series,
        Integer repeticiones,
        Double peso
) {}
