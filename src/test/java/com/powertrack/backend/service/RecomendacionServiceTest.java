package com.powertrack.backend.service;

import com.powertrack.backend.domain.service.RecomendacionService;
import com.powertrack.backend.ui.dto.PerfilDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@ExtendWith(MockitoExtension.class)
public class RecomendacionServiceTest {

    @InjectMocks
    private RecomendacionService recomendacionService;

    @Test
    void deberiaRetornar7CuandoHayLesion() {
        PerfilDTO perfil = new PerfilDTO(null, null, 1, 0, null, 5, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(7, resultado);
    }

    @Test
    void deberiaRetornar0CuandoObjetivo0YNivel0() {
        PerfilDTO perfil = new PerfilDTO(null, null, 0, 0, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(0, resultado);
    }

    @Test
    void deberiaRetornar1CuandoObjetivo0YNivel1() {
        PerfilDTO perfil = new PerfilDTO(null, null, 0, 1, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(1, resultado);
    }

    @Test
    void deberiaRetornar2CuandoObjetivo0YNivel2() {
        PerfilDTO perfil = new PerfilDTO(null, null, 0, 2, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(2, resultado);
    }

    @Test
    void deberiaRetornar3CuandoObjetivo1YNivel0() {
        PerfilDTO perfil = new PerfilDTO(null, null, 1, 0, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(3, resultado);
    }

    @Test
    void deberiaRetornar3CuandoObjetivo1YNivel1() {
        PerfilDTO perfil = new PerfilDTO(null, null, 1, 1, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(3, resultado);
    }

    @Test
    void deberiaRetornar4CuandoObjetivo1YNivel2() {
        PerfilDTO perfil = new PerfilDTO(null, null, 1, 2, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(4, resultado);
    }

    @Test
    void deberiaRetornar5CuandoObjetivo2() {
        PerfilDTO perfil = new PerfilDTO(null, null, 2, 0, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(5, resultado);
    }

    @Test
    void deberiaRetornar6CuandoObjetivo3() {
        PerfilDTO perfil = new PerfilDTO(null, null, 3, 0, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(6, resultado);
    }


    @Test
    void deberiaRetornar0CuandoLesionEsCero() {
        PerfilDTO perfil = new PerfilDTO(null, null, 0, 0, null, 0, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(0, resultado);
        assertNotEquals(7, resultado);
    }

    @Test
    void deberiaRetornar0CuandoLesionEsNull() {
        PerfilDTO perfil = new PerfilDTO(null, null, 0, 0, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(0, resultado);
        assertNotEquals(7, resultado);
    }

    @Test
    void deberiaRetornar0CuandoObjetivoYNivelSonNull() {
        PerfilDTO perfil = new PerfilDTO(null, null, null, null, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(0, resultado);
    }

    @Test
    void deberiaRetornar1CuandoObjetivoEsNullSeTrataComoVolumenYNivel1() {
        PerfilDTO perfil = new PerfilDTO(null, null, null, 1, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(1, resultado);
    }

    @Test
    void deberiaRetornar0CuandoObjetivoDesconocido() {
        PerfilDTO perfil = new PerfilDTO(null, null, 99, 0, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(0, resultado);
    }

    @Test
    void deberiaRetornar7CuandoLesionNegativa() {
        PerfilDTO perfil = new PerfilDTO(null, null, 2, 3, null, -1, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(7, resultado);
    }

    @Test
    void deberiaRetornar7SinImportarObjetivoNiNivelSiHayLesion() {
        PerfilDTO perfil = new PerfilDTO(null, null, 3, 5, null, 1, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(7, resultado);
        assertNotEquals(6, resultado);
    }

    @Test
    void deberiaRetornar2CuandoObjetivo0YNivelMuyAlto() {
        PerfilDTO perfil = new PerfilDTO(null, null, 0, 10, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(2, resultado);
    }

    @Test
    void deberiaRetornar4CuandoObjetivo1YNivelMuyAlto() {
        PerfilDTO perfil = new PerfilDTO(null, null, 1, 99, null, null, null, null);

        int resultado = recomendacionService.calcular(perfil);

        assertEquals(4, resultado);
    }
}
