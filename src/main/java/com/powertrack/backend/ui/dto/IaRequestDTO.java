package com.powertrack.backend.ui.dto;

public record IaRequestDTO(
        Integer genero,
        Integer edad,
        Integer objetivo,
        Integer nivel,
        Integer dias,
        Integer lesion,
        Integer pref,
        Integer peso_cat
) {}
