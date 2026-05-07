package com.powertrack.backend.domain.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.entity.PlanNutricionalEntity;
import com.powertrack.backend.data.PlanNutricionalRepository;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.PlanNutricionalMapper;
import com.powertrack.backend.domain.model.PlanNutricional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanNutricionalService {

    private final PlanNutricionalRepository planRepository;
    private final PlanNutricionalMapper mapper;

    public PlanNutricionalService(PlanNutricionalRepository planRepository, PlanNutricionalMapper mapper) {
        this.planRepository = planRepository;
        this.mapper = mapper;
    }

    public List<PlanNutricional> getAll() {
        return planRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    public PlanNutricional getById(Long id) {
        return planRepository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new BadRequestException(Constantes.PLAN_NO_ENCONTRADO));
    }

    public PlanNutricional getByTipo(Integer tipo) {
        return planRepository.findFirstByTipo(tipo)
                .map(mapper::toDomain)
                .orElseThrow(() -> new BadRequestException(Constantes.PLAN_NO_ENCONTRADO));
    }

    public PlanNutricional save(PlanNutricional plan) {
        PlanNutricionalEntity entity = new PlanNutricionalEntity();
        entity.setNombre(plan.nombre());
        entity.setDescripcion(plan.descripcion());
        entity.setTipo(plan.tipo());
        entity.setCaloriasObjetivo(plan.caloriasObjetivo());
        entity.setProteinasObjetivo(plan.proteinasObjetivo());
        entity.setCarbohidratosObjetivo(plan.carbohidratosObjetivo());
        entity.setGrasasObjetivo(plan.grasasObjetivo());
        return mapper.toDomain(planRepository.save(entity));
    }

    public void delete(Long id) {
        if (!planRepository.existsById(id)) {
            throw new BadRequestException(Constantes.PLAN_NO_ENCONTRADO);
        }
        planRepository.deleteById(id);
    }
}
