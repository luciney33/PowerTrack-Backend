package com.powertrack.backend.domain.service;

import com.powertrack.backend.ui.dto.PerfilDTO;
import org.springframework.stereotype.Service;

@Service
public class RecomendacionService {
    public int calcular(PerfilDTO perfil) {

        if (perfil.lesion() != null && perfil.lesion() != 0) {
            return 7;
        }

        int objetivo = perfil.objetivo() != null ? perfil.objetivo() : 0;
        int nivel = perfil.nivel() != null ? perfil.nivel() : 0;

        return switch (objetivo) {
            case 0 ->
                    switch (nivel) {
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
