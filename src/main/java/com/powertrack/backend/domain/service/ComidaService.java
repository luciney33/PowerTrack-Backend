package com.powertrack.backend.domain.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.entity.ComidaEntity;
import com.powertrack.backend.data.ComidaRepository;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.ComidaMapper;
import com.powertrack.backend.domain.model.Comida;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComidaService {

    private final ComidaRepository comidaRepository;
    private final ComidaMapper comidaMapper;

    public ComidaService(ComidaRepository comidaRepository, ComidaMapper
            comidaMapper) {
        this.comidaRepository = comidaRepository;
        this.comidaMapper = comidaMapper;
    }

    public List<Comida> getAll() {
        return comidaRepository.findAll().stream()
                .map(comidaMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Comida getById(Long id) {
        return comidaRepository.findById(id)
                .map(comidaMapper::toDomain)
                .orElseThrow(() -> new
                        BadRequestException(Constantes.COMIDA_NO_ENCONTRADA));
    }

    public List<Comida> getByCategoria(String categoria) {
        return comidaRepository.findByCategoria(categoria.toUpperCase()).stream()
                .map(comidaMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Comida save(Comida comida) {
        ComidaEntity entity = new ComidaEntity();
        entity.setNombre(comida.nombre());
        entity.setCalorias(comida.calorias());
        entity.setProteinas(comida.proteinas());
        entity.setCarbohidratos(comida.carbohidratos());
        entity.setGrasas(comida.grasas());
        entity.setCategoria(comida.categoria().toUpperCase());
        return comidaMapper.toDomain(comidaRepository.save(entity));
    }

    public void delete(Long id) {
        if (!comidaRepository.existsById(id)) {
            throw new BadRequestException(Constantes.COMIDA_NO_ENCONTRADA);
        }
        comidaRepository.deleteById(id);
    }
}
