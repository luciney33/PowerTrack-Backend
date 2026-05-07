package com.powertrack.backend.domain.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.EjercicioRepository;
import com.powertrack.backend.data.entity.EjercicioEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.EjercicioMapper;
import com.powertrack.backend.domain.model.Ejercicio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EjercicioService {
    private final EjercicioRepository ejercicioRepository;
    private final EjercicioMapper ejercicioMapper;

    public EjercicioService(EjercicioRepository ejercicioRepository,
                            EjercicioMapper ejercicioMapper) {
        this.ejercicioRepository = ejercicioRepository;
        this.ejercicioMapper = ejercicioMapper;
    }

    public List<Ejercicio> getAll() {
        return ejercicioRepository.findAll().stream()
                .map(ejercicioMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Ejercicio getById(Long id) {
        return ejercicioRepository.findById(id)
                .map(ejercicioMapper::toDomain)
                .orElseThrow(() -> new
                        BadRequestException(Constantes.EJERCICIO_NO_ENCONTRADO));
    }

    public List<Ejercicio> getByCategoria(String categoria) {
        return ejercicioRepository.findByTipoEntrenamiento(categoria.toUpperCase())
                .stream()
                .map(ejercicioMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Ejercicio save(Ejercicio ejercicio) {
        EjercicioEntity entity = new EjercicioEntity();
        entity.setNombre(ejercicio.nombre());
        entity.setTipoEntrenamiento(ejercicio.tipoEntrenamiento());
        entity.setImagenUrl(ejercicio.imagenUrl());
        entity.setDescripcion(ejercicio.descripcion());
        return ejercicioMapper.toDomain(ejercicioRepository.save(entity));
    }

    public Ejercicio update(Long id, Ejercicio ejercicio) {
        EjercicioEntity entity = ejercicioRepository.findById(id)
                .orElseThrow(() -> new
                        BadRequestException(Constantes.EJERCICIO_NO_ENCONTRADO));
        entity.setNombre(ejercicio.nombre());
        entity.setTipoEntrenamiento(ejercicio.tipoEntrenamiento());
        entity.setImagenUrl(ejercicio.imagenUrl());
        entity.setDescripcion(ejercicio.descripcion());
        return ejercicioMapper.toDomain(ejercicioRepository.save(entity));
    }

    public void delete(Long id) {
        if (!ejercicioRepository.existsById(id)) {
            throw new BadRequestException(Constantes.EJERCICIO_NO_ENCONTRADO);
        }
        ejercicioRepository.deleteById(id);
    }
}
