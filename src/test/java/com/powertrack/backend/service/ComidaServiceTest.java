package com.powertrack.backend.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.ComidaRepository;
import com.powertrack.backend.data.entity.ComidaEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.ComidaMapper;
import com.powertrack.backend.domain.model.Comida;
import com.powertrack.backend.domain.service.ComidaService;
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
public class ComidaServiceTest {

    @Mock private ComidaRepository comidaRepository;
    @Mock private ComidaMapper comidaMapper;

    @InjectMocks
    private ComidaService comidaService;


    @Test
    void deberiaRetornarTodasLasComidas() {
        ComidaEntity entity1 = new ComidaEntity();
        ComidaEntity entity2 = new ComidaEntity();
        Comida comida1 = new Comida(1L, "Pollo", 200, 40.0, 0.0, 5.0, "ALMUERZO", null);
        Comida comida2 = new Comida(2L, "Arroz", 350, 7.0, 75.0, 1.0, "ALMUERZO", null);

        when(comidaRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(comidaMapper.toDomain(entity1)).thenReturn(comida1);
        when(comidaMapper.toDomain(entity2)).thenReturn(comida2);

        List<Comida> resultado = comidaService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("Pollo", resultado.get(0).nombre());
        assertEquals("Arroz", resultado.get(1).nombre());
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoHayComidas() {
        when(comidaRepository.findAll()).thenReturn(List.of());

        List<Comida> resultado = comidaService.getAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }



    @Test
    void deberiaRetornarComidaCuandoIdExiste() {
        ComidaEntity entity = new ComidaEntity();
        Comida comidaEsperada = new Comida(1L, "Avena", 370, 13.0, 66.0, 7.0, "DESAYUNO", null);

        when(comidaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(comidaMapper.toDomain(entity)).thenReturn(comidaEsperada);

        Comida resultado = comidaService.getById(1L);

        assertNotNull(resultado);
        assertEquals("Avena", resultado.nombre());
        assertEquals(1L, resultado.id());
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdNoExiste() {
        when(comidaRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> comidaService.getById(99L));

        assertEquals(Constantes.COMIDA_NO_ENCONTRADA, ex.getMessage());
    }


    @Test
    void deberiaRetornarComidasPorCategoria() {
        ComidaEntity entity1 = new ComidaEntity();
        ComidaEntity entity2 = new ComidaEntity();
        Comida comida1 = new Comida(1L, "Tostadas", 250, 8.0, 45.0, 3.0, "DESAYUNO", null);
        Comida comida2 = new Comida(2L, "Avena", 370, 13.0, 66.0, 7.0, "DESAYUNO", null);

        when(comidaRepository.findByCategoria("DESAYUNO")).thenReturn(List.of(entity1, entity2));
        when(comidaMapper.toDomain(entity1)).thenReturn(comida1);
        when(comidaMapper.toDomain(entity2)).thenReturn(comida2);

        List<Comida> resultado = comidaService.getByCategoria("DESAYUNO");

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(c -> "DESAYUNO".equals(c.categoria())));
    }

    @Test
    void deberiaConvertirCategoriaAMayusculasAntesDeConsultar() {
        when(comidaRepository.findByCategoria("CENA")).thenReturn(List.of());

        comidaService.getByCategoria("cena");

        verify(comidaRepository).findByCategoria("CENA");
    }

    @Test
    void deberiaRetornarListaVaciaSiNingunaComidaCorrespondeALaCategoria() {
        when(comidaRepository.findByCategoria("MERIENDA")).thenReturn(List.of());

        List<Comida> resultado = comidaService.getByCategoria("MERIENDA");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void deberiaGuardarComidaCorrectamente() {
        Comida comidaEntrada = new Comida(null, "Salmon", 180, 25.0, 0.0, 10.0, "CENA", null);
        ComidaEntity entidadGuardada = new ComidaEntity();
        Comida comidaEsperada = new Comida(1L, "Salmon", 180, 25.0, 0.0, 10.0, "CENA", null);

        when(comidaRepository.save(any(ComidaEntity.class))).thenReturn(entidadGuardada);
        when(comidaMapper.toDomain(entidadGuardada)).thenReturn(comidaEsperada);

        Comida resultado = comidaService.save(comidaEntrada);

        assertNotNull(resultado);
        assertEquals("Salmon", resultado.nombre());
        verify(comidaRepository).save(any(ComidaEntity.class));
    }

    @Test
    void deberiaMappearTodosLosCamposYConvertirCategoriaAMayusculasAlGuardar() {
        Comida comidaEntrada = new Comida(null, "Yogur", 100, 10.0, 12.0, 2.5, "desayuno", null);
        ComidaEntity entidadGuardada = new ComidaEntity();
        Comida comidaEsperada = new Comida(1L, "Yogur", 100, 10.0, 12.0, 2.5, "DESAYUNO", null);

        when(comidaRepository.save(any(ComidaEntity.class))).thenAnswer(inv -> {
            ComidaEntity e = inv.getArgument(0);
            assertEquals("Yogur", e.getNombre());
            assertEquals(100, e.getCalorias());
            assertEquals(10.0, e.getProteinas());
            assertEquals(12.0, e.getCarbohidratos());
            assertEquals(2.5, e.getGrasas());
            assertEquals("DESAYUNO", e.getCategoria());
            return entidadGuardada;
        });
        when(comidaMapper.toDomain(entidadGuardada)).thenReturn(comidaEsperada);

        comidaService.save(comidaEntrada);

        verify(comidaRepository).save(any(ComidaEntity.class));
    }


    @Test
    void deberiaEliminarComidaCuandoExiste() {
        when(comidaRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> comidaService.delete(1L));

        verify(comidaRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarSiIdNoExiste() {
        when(comidaRepository.existsById(99L)).thenReturn(false);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> comidaService.delete(99L));

        assertEquals(Constantes.COMIDA_NO_ENCONTRADA, ex.getMessage());
        verify(comidaRepository, never()).deleteById(any());
    }
}
