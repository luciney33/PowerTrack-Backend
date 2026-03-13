package com.powertrack.backend.domain.service;

import jakarta.persistence.EntityNotFoundException;
import 

import com.powertrack.backend.data.RutinaRepository;
import com.powertrack.backend

.data.UsuarioRepository;
import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend

.data.entity.UsuarioEntity;
import com.powertrack.backend.domain.mapper.RutinaMapper;
import com.powertrack.backend.domain.model.Rutina;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RutinaService {
    private final RutinaRepository rutinaRepository;
    private final RutinaMapper rutinaMapper;
    private final UsuarioRepository usuarioRepository;

    public RutinaService(RutinaRepository rutinaRepository,
                         RutinaMapper rutinaMapper,
                         UsuarioRepository usuarioRepository) {
        this.rutinaRepository = rutinaRepository;
        this.rutinaMapper = rutinaMapper;
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional(readOnly = true)
    public List<Rutina> getAll() {
        return rutinaRepository.findAllWithEjercicios()
                .stream().map(rutinaMapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Rutina getById(Long id) {
        return rutinaRepository.findByIdWithEjercicios(id)
                .map(rutinaMapper::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.NO_ENCONTRADO));
    }

    @Transactional
    public Rutina save(Rutina rutina) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException(Constantes.MSG_NO_USUARIO_AUTENTICADO);
        }
        String username = authentication.getName();

        UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(username);
        if (usuarioEntity == null) {
            throw new UsernameNotFoundException(Constantes.MSG_USUARIO_NO_ENCONTRADO_CON_NOMBRE + username);
        }

        RutinaEntity entity = rutinaMapper.toEntity(rutina);
        entity.setUsuarioId(usuarioEntity.getId());

        RutinaEntity saved = rutinaRepository.save(entity);
        return rutinaMapper.toDomain(saved);
    }

    @Transactional
    public Rutina update(Long id, Rutina rutina) {
        return rutinaRepository.findById(id)
                .map(existing -> {
                    RutinaEntity updated = rutinaMapper.toEntity(rutina);
                    updated.setId(existing.getId());
                    updated.setUsuarioId(existing.getUsuarioId()); // Mantener el usuario original
                    return rutinaMapper.toDomain(rutinaRepository.save(updated));
                })
                .orElseThrow(() -> new EntityNotFoundException(Constantes.NO_ENCONTRADO));
    }

    @Transactional
    public void delete(Long id) {
        if (rutinaRepository.existsById(id)) {
            rutinaRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(Constantes.NO_ENCONTRADO);
        }
    }
}
