package com.powertrack.backend.domain.service;

import com.powertrack.backend.ui.dto.IaRequestDTO;
import com.powertrack.backend.ui.dto.PerfilDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class RecomendacionService {

    private final RestTemplate restTemplate;

    @Value("${ia.fastapi.url}")
    private String fastapiUrl;

    public RecomendacionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int calcular(PerfilDTO perfil) {
        try {
            IaRequestDTO body = new IaRequestDTO(
                    perfil.genero()            != null ? perfil.genero()            : 0,
                    perfil.edad()              != null ? perfil.edad()              : 0,
                    perfil.objetivo()          != null ? perfil.objetivo()          : 0,
                    perfil.nivel()             != null ? perfil.nivel()             : 0,
                    perfil.diasEntrenamiento() != null ? perfil.diasEntrenamiento() : 3,
                    perfil.lesion()            != null ? perfil.lesion()            : 0,
                    perfil.preferencia()       != null ? perfil.preferencia()       : 0,
                    perfil.pesoCat()           != null ? perfil.pesoCat()           : 0
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<IaRequestDTO> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    fastapiUrl, request, Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (Integer) response.getBody().get("recomendacion");
            }

        } catch (Exception e) {
            return calcularFallback(perfil);
        }

        return calcularFallback(perfil);
    }

    private int calcularFallback(PerfilDTO perfil) {
        if (perfil.lesion() != null && perfil.lesion() != 0) return 7;

        int objetivo = perfil.objetivo() != null ? perfil.objetivo() : 0;
        int nivel    = perfil.nivel()    != null ? perfil.nivel()    : 0;

        return switch (objetivo) {
            case 0 -> switch (nivel) {
                case 0  -> 0;
                case 1  -> 1;
                default -> 2;
            };
            case 1 -> nivel >= 2 ? 4 : 3;
            case 2 -> 5;
            case 3 -> 6;
            default -> 0;
        };
    }
}
