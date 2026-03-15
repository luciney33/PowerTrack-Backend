package com.powertrack.backend.domain.mapper;

import com.powertrack.backend.data.entity.PlanNutricionalEntity;
import com.powertrack.backend.domain.model.PlanNutricional;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class PlanNutricionalMapper {

    private final ComidaMapper comidaMapper;

    public PlanNutricionalMapper(ComidaMapper comidaMapper) {
        this.comidaMapper = comidaMapper;
    }

    public PlanNutricional toDomain(PlanNutricionalEntity entity) {
        if (entity == null) return null;
        return new PlanNutricional(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getTipo(),
                entity.getCaloriasObjetivo(),
                entity.getProteinasObjetivo(),
                entity.getCarbohidratosObjetivo(),
                entity.getGrasasObjetivo(),
                entity.getComidas().stream()
                        .map(comidaMapper::toDomain)
                        .collect(Collectors.toList())
        );
    }
}
