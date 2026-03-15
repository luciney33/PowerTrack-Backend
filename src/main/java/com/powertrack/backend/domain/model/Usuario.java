package com.powertrack.backend.domain.model;


import java.time.LocalDateTime;


public record Usuario(
        Long id,
        String username,
        String password,
        String email,
        String nombre,
        Rol rol,
        boolean activo,
        String codigoActivacion,
        LocalDateTime expiracionCodigo,
        Integer genero,
        Integer edad,
        Integer objetivo,
        Integer nivel,
        Integer diasEntrenamiento,
        Integer lesion,
        Integer preferencia,
        Integer recomendacion,
        boolean formularioCompletado
) {}
