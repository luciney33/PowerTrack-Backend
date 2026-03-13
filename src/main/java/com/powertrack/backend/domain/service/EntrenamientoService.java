package com.powertrack.backend.domain.service;

import jakarta.persistence.EntityNotFoundException;
import 

import com.powertrack.backend

.data.EntrenamientoRepository;
import com.powertrack.backend

.data.UsuarioRepository;
import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend

.data.entity.UsuarioEntity;
import com.powertrack.backend

.domain.mapper.EntrenamientoMapper;
import com.powertrack.backend

.domain.model.Entrenamiento;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntrenamientoService {
    private final EntrenamientoRepository entrenamientoRepository;
    private final EntrenamientoMapper entrenamientoMapper;
    private final UsuarioRepository usuarioRepository;

    public EntrenamientoService(EntrenamientoRepository entrenamientoRepository,
                                EntrenamientoMapper entrenamientoMapper,
                                UsuarioRepository usuarioRepository) {
        this.entrenamientoRepository = entrenamientoRepository;
        this.entrenamientoMapper = entrenamientoMapper;
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional(readOnly = true)
    public List<Entrenamiento> getAll() {
        return entrenamientoRepository.findAllWithEjercicios()
                .stream().map(entrenamientoMapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Entrenamiento getById(Long id) {
        return entrenamientoRepository.findByIdWithEjercicios(id)
                .map(entrenamientoMapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.NO_ENCONTRADO));
    }

    @Transactional
    public Entrenamiento save(Entrenamiento entrenamiento) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException(Constantes.MSG_NO_USUARIO_AUTENTICADO);
        }
        String username = authentication.getName();

        UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(username);
        if (usuarioEntity == null) {
            throw new UsernameNotFoundException(Constantes.MSG_USUARIO_NO_ENCONTRADO_CON_NOMBRE + username);
        }

        RutinaEntity entity = entrenamientoMapper.toEntity(entrenamiento);
        entity.setUsuarioId(usuarioEntity.getId());

        RutinaEntity saved = entrenamientoRepository.save(entity);
        return entrenamientoMapper.toDomain(saved);
    }

    @Transactional
    public Entrenamiento update(Long id, Entrenamiento entrenamiento) {
        return entrenamientoRepository.findById(id)
                .map(existing -> {
                    RutinaEntity updated = entrenamientoMapper.toEntity(entrenamiento);
                    updated.setId(existing.getId());
                    updated.setUsuarioId(existing.getUsuarioId()); // Mantener el usuario original
                    return entrenamientoMapper.toDomain(entrenamientoRepository.save(updated));
                })
                .orElseThrow(() -> new EntityNotFoundException(Constantes.NO_ENCONTRADO));
    }

    @Transactional
    public void delete(Long id) {
        if (entrenamientoRepository.existsById(id)) {
            entrenamientoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(Constantes.NO_ENCONTRADO);
        }
    }
}
