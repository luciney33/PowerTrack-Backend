package com.powertrack.backend.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.PlanNutricionalRepository;
import com.powertrack.backend.data.entity.PlanNutricionalEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.PlanNutricionalMapper;
import com.powertrack.backend.domain.model.PlanNutricional;
import com.powertrack.backend.domain.service.PlanNutricionalService;
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
public class PlanNutricionalServiceTest {

    @Mock private PlanNutricionalRepository planRepository;
    @Mock private PlanNutricionalMapper mapper;

    @InjectMocks
    private PlanNutricionalService planNutricionalService;


    @Test
    void deberiaRetornarTodosLosPlanes() {
        PlanNutricionalEntity entity1 = new PlanNutricionalEntity();
        entity1.setNombre("Plan Volumen");
        PlanNutricionalEntity entity2 = new PlanNutricionalEntity();
        entity2.setNombre("Plan Definicion");
        PlanNutricional plan1 = new PlanNutricional(1L, "Plan Volumen", "Desc", 1, 3000, 180.0, 350.0, 80.0, List.of());
        PlanNutricional plan2 = new PlanNutricional(2L, "Plan Definicion", "Desc", 2, 2000, 160.0, 200.0, 60.0, List.of());

        when(planRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(plan1);
        when(mapper.toDomain(entity2)).thenReturn(plan2);

        List<PlanNutricional> resultado = planNutricionalService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("Plan Volumen", resultado.get(0).nombre());
        assertEquals("Plan Definicion", resultado.get(1).nombre());
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoHayPlanes() {
        when(planRepository.findAll()).thenReturn(List.of());

        List<PlanNutricional> resultado = planNutricionalService.getAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void deberiaRetornarPlanCuandoIdExiste() {
        PlanNutricionalEntity entity = new PlanNutricionalEntity();
        entity.setNombre("Plan Mantenimiento");
        PlanNutricional planEsperado = new PlanNutricional(1L, "Plan Mantenimiento", "Desc", 3, 2500, 150.0, 280.0, 70.0, List.of());

        when(planRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(planEsperado);

        PlanNutricional resultado = planNutricionalService.getById(1L);

        assertNotNull(resultado);
        assertEquals("Plan Mantenimiento", resultado.nombre());
        assertEquals(1L, resultado.id());
    }

    @Test
    void deberiaLanzarExcepcionCuandoIdNoExiste() {
        when(planRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> planNutricionalService.getById(99L));

        assertEquals(Constantes.PLAN_NO_ENCONTRADO, ex.getMessage());
    }


    @Test
    void deberiaRetornarPlanCuandoTipoExiste() {
        PlanNutricionalEntity entity = new PlanNutricionalEntity();
        entity.setTipo(2);
        PlanNutricional planEsperado = new PlanNutricional(1L, "Plan Definicion", "Desc", 2, 2000, 160.0, 200.0, 60.0, List.of());

        when(planRepository.findFirstByTipo(2)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(planEsperado);

        PlanNutricional resultado = planNutricionalService.getByTipo(2);

        assertNotNull(resultado);
        assertEquals(2, resultado.tipo());
    }

    @Test
    void deberiaLanzarExcepcionCuandoTipoNoExiste() {
        when(planRepository.findFirstByTipo(99)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> planNutricionalService.getByTipo(99));

        assertEquals(Constantes.PLAN_NO_ENCONTRADO, ex.getMessage());
    }


    @Test
    void deberiaGuardarPlanCorrectamente() {
        PlanNutricional planEntrada = new PlanNutricional(null, "Plan Nuevo", "Desc", 1, 3000, 180.0, 350.0, 80.0, List.of());
        PlanNutricionalEntity entidadGuardada = new PlanNutricionalEntity();
        PlanNutricional planEsperado = new PlanNutricional(1L, "Plan Nuevo", "Desc", 1, 3000, 180.0, 350.0, 80.0, List.of());

        when(planRepository.save(any(PlanNutricionalEntity.class))).thenReturn(entidadGuardada);
        when(mapper.toDomain(entidadGuardada)).thenReturn(planEsperado);

        PlanNutricional resultado = planNutricionalService.save(planEntrada);

        assertNotNull(resultado);
        assertEquals("Plan Nuevo", resultado.nombre());
        verify(planRepository).save(any(PlanNutricionalEntity.class));
    }

    @Test
    void deberiaMappearTodosLosCamposNuticionalesAlGuardar() {
        PlanNutricional planEntrada = new PlanNutricional(null, "Plan X", "Desc X", 4, 2800, 170.0, 300.0, 75.0, List.of());
        PlanNutricionalEntity entidadGuardada = new PlanNutricionalEntity();
        PlanNutricional planEsperado = new PlanNutricional(1L, "Plan X", "Desc X", 4, 2800, 170.0, 300.0, 75.0, List.of());

        when(planRepository.save(any(PlanNutricionalEntity.class))).thenAnswer(inv -> {
            PlanNutricionalEntity e = inv.getArgument(0);
            assertEquals("Plan X", e.getNombre());
            assertEquals("Desc X", e.getDescripcion());
            assertEquals(4, e.getTipo());
            assertEquals(2800, e.getCaloriasObjetivo());
            assertEquals(170.0, e.getProteinasObjetivo());
            assertEquals(300.0, e.getCarbohidratosObjetivo());
            assertEquals(75.0, e.getGrasasObjetivo());
            return entidadGuardada;
        });
        when(mapper.toDomain(entidadGuardada)).thenReturn(planEsperado);

        planNutricionalService.save(planEntrada);

        verify(planRepository).save(any(PlanNutricionalEntity.class));
    }


    @Test
    void deberiaEliminarPlanCuandoExiste() {
        when(planRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> planNutricionalService.delete(1L));

        verify(planRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarSiIdNoExiste() {
        when(planRepository.existsById(99L)).thenReturn(false);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> planNutricionalService.delete(99L));

        assertEquals(Constantes.PLAN_NO_ENCONTRADO, ex.getMessage());
        verify(planRepository, never()).deleteById(any());
    }
}
