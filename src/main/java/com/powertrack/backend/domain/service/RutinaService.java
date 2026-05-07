package com.powertrack.backend.domain.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.data.RutinaRepository;
import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend.domain.mapper.RutinaMapper;
import com.powertrack.backend.domain.model.Rutina;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutinaService {
    private final RutinaRepository rutinaRepository;
    private final RutinaMapper rutinaMapper;

    public RutinaService(RutinaRepository rutinaRepository, RutinaMapper
            rutinaMapper) {
        this.rutinaRepository = rutinaRepository;
        this.rutinaMapper = rutinaMapper;
    }

    public List<Rutina> getAll() {
        return rutinaRepository.findAll().stream()
                .map(rutinaMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Rutina getById(Long id) {
        return rutinaRepository.findById(id)
                .map(rutinaMapper::toDomain)
                .orElseThrow(() -> new
                        BadRequestException(Constantes.RUTINA_NO_ENCONTRADA));
    }

    public Rutina getByTipo(Integer tipo) {
        return rutinaRepository.findFirstByTipo(tipo)
                .map(rutinaMapper::toDomain)
                .orElseThrow(() -> new BadRequestException(Constantes.RUTINA_NO_ENCONTRADA));
    }

    public Rutina save(Rutina rutina) {
        RutinaEntity entity = new RutinaEntity();
        entity.setNombre(rutina.nombre());
        entity.setDescripcion(rutina.descripcion());
        entity.setTipo(rutina.tipo());
        return rutinaMapper.toDomain(rutinaRepository.save(entity));
    }

    public Rutina update(Long id, Rutina rutina) {
        RutinaEntity entity = rutinaRepository.findById(id)
                .orElseThrow(() -> new
                        BadRequestException(Constantes.RUTINA_NO_ENCONTRADA));
        entity.setNombre(rutina.nombre());
        entity.setDescripcion(rutina.descripcion());
        entity.setTipo(rutina.tipo());
        return rutinaMapper.toDomain(rutinaRepository.save(entity));
    }

    public void delete(Long id) {
        if (!rutinaRepository.existsById(id)) {
            throw new BadRequestException(Constantes.RUTINA_NO_ENCONTRADA);
        }
        rutinaRepository.deleteById(id);
    }
}
