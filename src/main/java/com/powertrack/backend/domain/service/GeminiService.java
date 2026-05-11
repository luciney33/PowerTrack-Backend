package com.powertrack.backend.domain.service;

import com.powertrack.backend.domain.model.GeminiRequest;
import com.powertrack.backend.domain.model.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {


    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generarDescripcionRutina(int objetivo, int nivel, int dias, int lesion) {
        String prompt = String.format(
                "Eres un entrenador personal. Genera una descripción motivadora de máximo 3 frases para un usuario con objetivo: %s, nivel: %s, %d días/semana, lesión: %s. Solo texto fluido.",
                traducirObjetivo(objetivo), traducirNivel(nivel), dias, traducirLesion(lesion)
        );
        return llamarGemini(prompt);
    }

    public String generarConsejosNutricion(int objetivo, int genero, int edad, int pesoCat) {
        String prompt = String.format(
                "Eres un nutricionista. Genera 2-3 consejos nutricionales para: objetivo: %s, género: %s, edad: %s, categoría de peso: %s. Solo texto fluido sin listas.",
                traducirObjetivo(objetivo), traducirGenero(genero), traducirEdad(edad), traducirPesoCat(pesoCat)
        );
        return llamarGemini(prompt);
    }

    private String llamarGemini(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        GeminiRequest request = new GeminiRequest(
                List.of(new GeminiRequest.GeminiContent(
                        List.of(new GeminiRequest.GeminiPart(prompt))
                ))
        );

        ResponseEntity<GeminiResponse> response = restTemplate.postForEntity(
                url, request, GeminiResponse.class
        );

        return response.getBody()
                .candidates().get(0)
                .content().parts().get(0)
                .text();
    }

    private String traducirObjetivo(int objetivo) {
        return switch (objetivo) {
            case 0 -> "ganar músculo (volumen)";
            case 1 -> "perder grasa (definición)";
            case 2 -> "mantenimiento";
            case 3 -> "pérdida de peso";
            default -> "general";
        };
    }

    private String traducirNivel(int nivel) {
        return switch (nivel) {
            case 0 -> "principiante";
            case 1 -> "intermedio";
            case 2 -> "avanzado";
            default -> "principiante";
        };
    }

    private String traducirLesion(int lesion) {
        return switch (lesion) {
            case 0 -> "ninguna";
            case 1 -> "lesión de espalda";
            case 2 -> "lesión de rodilla";
            case 3 -> "lesión de hombro";
            case 4 -> "lesión de tobillo";
            default -> "ninguna";
        };
    }

    private String traducirPesoCat(int pesoCat) {
        return switch (pesoCat) {
            case 0 -> "bajo peso (menos de 55 kg)";
            case 1 -> "peso normal (55-70 kg)";
            case 2 -> "sobrepeso leve (71-85 kg)";
            case 3 -> "sobrepeso moderado (86-100 kg)";
            case 4 -> "obesidad (más de 100 kg)";
            default -> "peso normal";
        };
    }

    private String traducirGenero(int genero) {
        return switch (genero) {
            case 0 -> "hombre";
            case 1 -> "mujer";
            default -> "persona";
        };
    }

    private String traducirEdad(int edad) {
        return switch (edad) {
            case 0 -> "16-29 años";
            case 1 -> "30-39 años";
            case 2 -> "40-49 años";
            case 3 -> "50+ años";
            default -> "adulto";
        };
    }
}
