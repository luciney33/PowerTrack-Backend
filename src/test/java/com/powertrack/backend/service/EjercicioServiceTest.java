package com.powertrack.backend.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.EjercicioRepository;
import com.powertrack.backend.data.entity.EjercicioEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.EjercicioMapper;
import com.powertrack.backend.domain.model.Ejercicio;
import com.powertrack.backend.domain.service.EjercicioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EjercicioServiceTest {

    @Mock private EjercicioRepository ejercicioRepository;
    @Mock private EjercicioMapper ejercicioMapper;

    @InjectMocks
    private EjercicioService ejercicioService;


    @Test
    void deberiaRetornarTodosLosEjercicios() {
        EjercicioEntity entity1 = new EjercicioEntity();
        entity1.setNombre("Press Banca");
        EjercicioEntity entity2 = new EjercicioEntity();
        entity2.setNombre("Sentadilla");
        Ejercicio ejercicio1 = new Ejercicio(1L, "Press Banca", "PECHO", null, null);
        Ejercicio ejercicio2 = new Ejercicio(2L, "Sentadilla", "PIERNAS", null, null);

        when(ejercicioRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(ejercicioMapper.toDomain(entity1)).thenReturn(ejercicio1);
        when(ejercicioMapper.toDomain(entity2)).thenReturn(ejercicio2);

        List<Ejercicio> resultado = ejercicioService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("Press Banca", resultado.get(0).nombre());
        assertEquals("Sentadilla", resultado.get(1).nombre());
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoHayEjercicios() {
        when(ejercicioRepository.findAll()).thenReturn(List.of());

        List<Ejercicio> resultado = ejercicioService.getAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void deberiaRetornarEjercicioCuandoIdExiste() {
        EjercicioEntity entity = new EjercicioEntity();
        entity.setNombre("Dominadas");
        Ejercicio ejercicioEsperado = new Ejercicio(1L, "Dominadas", "ESPALDA", null, null);

        when(ejercicioRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(ejercicioMapper.toDomain(entity)).thenReturn(ejercicioEsperado);

        Ejercicio resultado = ejercicioService.getById(1L);

        assertNotNull(resultado);
        assertEquals("Dominadas", resultado.nombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdNoExiste() {
        when(ejercicioRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> ejercicioService.getById(99L));

        assertEquals(Constantes.EJERCICIO_NO_ENCONTRADO, ex.getMessage());
    }


    @Test
    void deberiaRetornarEjerciciosPorCategoria() {
        EjercicioEntity entity1 = new EjercicioEntity();
        entity1.setNombre("Press Banca");
        EjercicioEntity entity2 = new EjercicioEntity();
        entity2.setNombre("Aperturas");
        Ejercicio ejercicio1 = new Ejercicio(1L, "Press Banca", "PECHO", null, null);
        Ejercicio ejercicio2 = new Ejercicio(2L, "Aperturas", "PECHO", null, null);

        when(ejercicioRepository.findByTipoEntrenamiento("PECHO")).thenReturn(List.of(entity1, entity2));
        when(ejercicioMapper.toDomain(entity1)).thenReturn(ejercicio1);
        when(ejercicioMapper.toDomain(entity2)).thenReturn(ejercicio2);

        List<Ejercicio> resultado = ejercicioService.getByCategoria("PECHO");

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(e -> "PECHO".equals(e.tipoEntrenamiento())));
    }

    @Test
    void deberiaConvertirCategoriaAMayusculasAntesDeConsultar() {
        when(ejercicioRepository.findByTipoEntrenamiento("PECHO")).thenReturn(List.of());

        ejercicioService.getByCategoria("pecho");

        verify(ejercicioRepository).findByTipoEntrenamiento("PECHO");
    }

    @Test
    void deberiaRetornarListaVaciaSiNingunEjercicioCorrespondeALaCategoria() {
        when(ejercicioRepository.findByTipoEntrenamiento("CARDIO")).thenReturn(List.of());

        List<Ejercicio> resultado = ejercicioService.getByCategoria("CARDIO");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void deberiaGuardarEjercicioCorrectamente() {
        Ejercicio ejercicioEntrada = new Ejercicio(null, "Curl Biceps", "BRAZOS", null, "Ejercicio de bíceps");
        EjercicioEntity entidadGuardada = new EjercicioEntity();
        Ejercicio ejercicioEsperado = new Ejercicio(1L, "Curl Biceps", "BRAZOS", null, "Ejercicio de bíceps");

        when(ejercicioRepository.save(any(EjercicioEntity.class))).thenReturn(entidadGuardada);
        when(ejercicioMapper.toDomain(entidadGuardada)).thenReturn(ejercicioEsperado);

        Ejercicio resultado = ejercicioService.save(ejercicioEntrada);

        assertNotNull(resultado);
        assertEquals("Curl Biceps", resultado.nombre());
        verify(ejercicioRepository).save(any(EjercicioEntity.class));
    }

    @Test
    void deberiaMappearCamposCorrectosAlGuardar() {
        Ejercicio ejercicioEntrada = new Ejercicio(null, "Zancadas", "PIERNAS", "img.jpg", "Ejercicio piernas");
        EjercicioEntity entidadGuardada = new EjercicioEntity();
        Ejercicio ejercicioEsperado = new Ejercicio(1L, "Zancadas", "PIERNAS", "img.jpg", "Ejercicio piernas");

        when(ejercicioRepository.save(any(EjercicioEntity.class))).thenAnswer(inv -> {
            EjercicioEntity e = inv.getArgument(0);
            assertEquals("Zancadas", e.getNombre());
            assertEquals("PIERNAS", e.getTipoEntrenamiento());
            assertEquals("img.jpg", e.getImagenUrl());
            assertEquals("Ejercicio piernas", e.getDescripcion());
            return entidadGuardada;
        });
        when(ejercicioMapper.toDomain(entidadGuardada)).thenReturn(ejercicioEsperado);

        ejercicioService.save(ejercicioEntrada);

        verify(ejercicioRepository).save(any(EjercicioEntity.class));
    }


    @Test
    void deberiaActualizarEjercicioCuandoExiste() {
        Ejercicio ejercicioActualizado = new Ejercicio(1L, "Press Banca Inclinado", "PECHO", "nueva.jpg", "Nueva desc");
        EjercicioEntity entityExistente = new EjercicioEntity();
        entityExistente.setNombre("Press Banca");
        EjercicioEntity entidadGuardada = new EjercicioEntity();
        Ejercicio ejercicioEsperado = new Ejercicio(1L, "Press Banca Inclinado", "PECHO", "nueva.jpg", "Nueva desc");

        when(ejercicioRepository.findById(1L)).thenReturn(Optional.of(entityExistente));
        when(ejercicioRepository.save(entityExistente)).thenReturn(entidadGuardada);
        when(ejercicioMapper.toDomain(entidadGuardada)).thenReturn(ejercicioEsperado);

        Ejercicio resultado = ejercicioService.update(1L, ejercicioActualizado);

        assertNotNull(resultado);
        assertEquals("Press Banca Inclinado", entityExistente.getNombre());
        assertEquals("PECHO", entityExistente.getTipoEntrenamiento());
        assertEquals("nueva.jpg", entityExistente.getImagenUrl());
        assertEquals("Nueva desc", entityExistente.getDescripcion());
    }

    @Test
    void deberiaLanzarExcepcionAlActualizarSiIdNoExiste() {
        Ejercicio ejercicioActualizado = new Ejercicio(99L, "X", "Y", null, null);

        when(ejercicioRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> ejercicioService.update(99L, ejercicioActualizado));

        assertEquals(Constantes.EJERCICIO_NO_ENCONTRADO, ex.getMessage());
        verify(ejercicioRepository, never()).save(any());
    }


    @Test
    void deberiaEliminarEjercicioCuandoExiste() {
        when(ejercicioRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> ejercicioService.delete(1L));

        verify(ejercicioRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarSiIdNoExiste() {
        when(ejercicioRepository.existsById(99L)).thenReturn(false);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> ejercicioService.delete(99L));

        assertEquals(Constantes.EJERCICIO_NO_ENCONTRADO, ex.getMessage());
        verify(ejercicioRepository, never()).deleteById(any());
    }
}
