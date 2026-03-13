package com.powertrack.backend.domain.service;

import com.powertrack.backend

.data.EjercicioRepository;
import com.powertrack.backend

.domain.mapper.EjercicioMapper;
import com.powertrack.backend

.domain.model.Ejercicio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EjercicioService {
    private final EjercicioRepository ejerRepo;
    private final EjercicioMapper mapper;

    public EjercicioService(EjercicioRepository ejerRepo, EjercicioMapper mapper) {
        this.ejerRepo = ejerRepo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<Ejercicio> getAll() {
        return ejerRepo.findAll()
                .stream().map(mapper::toDomain).toList();
    }}
