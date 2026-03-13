package com.powertrack.backend.domain.model;


public record SecretoCompartido(
        Long id,
        Secreto secretoId,
        Usuario destinatarioId,
        byte[] claveSimétricaCifradaDestinatario) {
}

