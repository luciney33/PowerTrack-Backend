package com.powertrack.backend.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.RutinaRepository;
import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.RutinaMapper;
import com.powertrack.backend.domain.model.Rutina;
import com.powertrack.backend.domain.service.RutinaService;
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
public class RutinaServiceTest {

    @Mock private RutinaRepository rutinaRepository;
    @Mock private RutinaMapper rutinaMapper;

    @InjectMocks
    private RutinaService rutinaService;



    @Test
    void deberiaRetornarTodasLasRutinas() {
        RutinaEntity entity1 = new RutinaEntity();
        entity1.setNombre("Rutina A");
        RutinaEntity entity2 = new RutinaEntity();
        entity2.setNombre("Rutina B");
        Rutina rutina1 = new Rutina(1L, "Rutina A", "Desc A", 1, List.of());
        Rutina rutina2 = new Rutina(2L, "Rutina B", "Desc B", 2, List.of());

        when(rutinaRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(rutinaMapper.toDomain(entity1)).thenReturn(rutina1);
        when(rutinaMapper.toDomain(entity2)).thenReturn(rutina2);

        List<Rutina> resultado = rutinaService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("Rutina A", resultado.get(0).nombre());
        assertEquals("Rutina B", resultado.get(1).nombre());
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoHayRutinas() {
        when(rutinaRepository.findAll()).thenReturn(List.of());

        List<Rutina> resultado = rutinaService.getAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }



    @Test
    void deberiaRetornarRutinaCuandoIdExiste() {
        RutinaEntity entity = new RutinaEntity();
        entity.setNombre("Rutina Fuerza");
        Rutina rutina = new Rutina(1L, "Rutina Fuerza", "Descripción", 1, List.of());

        when(rutinaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(rutinaMapper.toDomain(entity)).thenReturn(rutina);

        Rutina resultado = rutinaService.getById(1L);

        assertNotNull(resultado);
        assertEquals("Rutina Fuerza", resultado.nombre());
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdNoExiste() {
        when(rutinaRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaService.getById(99L));

        assertEquals(Constantes.RUTINA_NO_ENCONTRADA, ex.getMessage());
    }



    @Test
    void deberiaRetornarRutinaCuandoTipoExiste() {
        RutinaEntity entity = new RutinaEntity();
        entity.setTipo(3);
        Rutina rutina = new Rutina(1L, "Rutina Cardio", "Descripción", 3, List.of());

        when(rutinaRepository.findByTipo(3)).thenReturn(Optional.of(entity));
        when(rutinaMapper.toDomain(entity)).thenReturn(rutina);

        Rutina resultado = rutinaService.getByTipo(3);

        assertNotNull(resultado);
        assertEquals(3, resultado.tipo());
    }

    @Test
    void deberiaLanzarExcepcionCuandoTipoNoExiste() {
        when(rutinaRepository.findByTipo(99)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaService.getByTipo(99));

        assertEquals(Constantes.RUTINA_NO_ENCONTRADA, ex.getMessage());
    }


    @Test
    void deberiaGuardarRutinaCorrectamente() {
        Rutina rutinaEntrada = new Rutina(null, "Nueva Rutina", "Descripción", 2, List.of());
        RutinaEntity entidadGuardada = new RutinaEntity();
        entidadGuardada.setNombre("Nueva Rutina");
        Rutina rutinaEsperada = new Rutina(1L, "Nueva Rutina", "Descripción", 2, List.of());

        when(rutinaRepository.save(any(RutinaEntity.class))).thenReturn(entidadGuardada);
        when(rutinaMapper.toDomain(entidadGuardada)).thenReturn(rutinaEsperada);

        Rutina resultado = rutinaService.save(rutinaEntrada);

        assertNotNull(resultado);
        assertEquals("Nueva Rutina", resultado.nombre());
        verify(rutinaRepository).save(any(RutinaEntity.class));
    }

    @Test
    void deberiaMappearCamposCorrectosAlGuardar() {
        Rutina rutinaEntrada = new Rutina(null, "Fuerza", "Desc", 1, List.of());
        RutinaEntity entidadCapturada = new RutinaEntity();
        Rutina rutinaEsperada = new Rutina(1L, "Fuerza", "Desc", 1, List.of());

        when(rutinaRepository.save(any(RutinaEntity.class))).thenAnswer(inv -> {
            RutinaEntity e = inv.getArgument(0);
            assertEquals("Fuerza", e.getNombre());
            assertEquals("Desc", e.getDescripcion());
            assertEquals(1, e.getTipo());
            return entidadCapturada;
        });
        when(rutinaMapper.toDomain(entidadCapturada)).thenReturn(rutinaEsperada);

        rutinaService.save(rutinaEntrada);

        verify(rutinaRepository).save(any(RutinaEntity.class));
    }


    @Test
    void deberiaActualizarRutinaCuandoExiste() {
        Rutina rutinaActualizada = new Rutina(1L, "Rutina Modificada", "Nueva Desc", 3, List.of());
        RutinaEntity entityExistente = new RutinaEntity();
        entityExistente.setNombre("Rutina Original");
        RutinaEntity entidadGuardada = new RutinaEntity();
        Rutina rutinaEsperada = new Rutina(1L, "Rutina Modificada", "Nueva Desc", 3, List.of());

        when(rutinaRepository.findById(1L)).thenReturn(Optional.of(entityExistente));
        when(rutinaRepository.save(entityExistente)).thenReturn(entidadGuardada);
        when(rutinaMapper.toDomain(entidadGuardada)).thenReturn(rutinaEsperada);

        Rutina resultado = rutinaService.update(1L, rutinaActualizada);

        assertNotNull(resultado);
        assertEquals("Rutina Modificada", resultado.nombre());
        assertEquals("Rutina Modificada", entityExistente.getNombre());
        assertEquals("Nueva Desc", entityExistente.getDescripcion());
        assertEquals(3, entityExistente.getTipo());
    }

    @Test
    void deberiaLanzarExcepcionAlActualizarSiIdNoExiste() {
        Rutina rutinaActualizada = new Rutina(99L, "X", "Y", 1, List.of());

        when(rutinaRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaService.update(99L, rutinaActualizada));

        assertEquals(Constantes.RUTINA_NO_ENCONTRADA, ex.getMessage());
        verify(rutinaRepository, never()).save(any());
    }


    @Test
    void deberiaEliminarRutinaCuandoExiste() {
        when(rutinaRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> rutinaService.delete(1L));

        verify(rutinaRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarSiIdNoExiste() {
        when(rutinaRepository.existsById(99L)).thenReturn(false);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> rutinaService.delete(99L));

        assertEquals(Constantes.RUTINA_NO_ENCONTRADA, ex.getMessage());
        verify(rutinaRepository, never()).deleteById(any());
    }
}
