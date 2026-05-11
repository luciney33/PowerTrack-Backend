package com.powertrack.backend.domain.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.entity.*;
import com.powertrack.backend.data.*;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.error.ForbiddenException;
import com.powertrack.backend.domain.mapper.RegistroEntrenamientoMapper;
import com.powertrack.backend.domain.model.RegistroEntrenamiento;
import com.powertrack.backend.ui.dto.RegistroDetalleDTO;
import com.powertrack.backend.ui.dto.RegistroEntrenamientoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroEntrenamientoService {

    private final RegistroEntrenamientoRepository registroRepository;
    private final UsuarioRepository usuarioRepository;
    private final RutinaRepository rutinaRepository;
    private final EjercicioRepository ejercicioRepository;
    private final RegistroEntrenamientoMapper mapper;

    public RegistroEntrenamientoService(RegistroEntrenamientoRepository registroRepository,
                                        UsuarioRepository usuarioRepository,
                                        RutinaRepository rutinaRepository,
                                        EjercicioRepository ejercicioRepository,
                                        RegistroEntrenamientoMapper mapper) {
        this.registroRepository = registroRepository;
        this.usuarioRepository = usuarioRepository;
        this.rutinaRepository = rutinaRepository;
        this.ejercicioRepository = ejercicioRepository;
        this.mapper = mapper;
    }

    public List<RegistroEntrenamiento> getAllByUsuario(String username) {
        return registroRepository.findByUsuarioUsername(username).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    public RegistroEntrenamiento getById(Long id, String username) {
        RegistroEntrenamientoEntity entity = registroRepository.findById(id)
                .orElseThrow(() -> new
                        BadRequestException(Constantes.REGISTRO_NO_ENCONTRADO));
        if (!entity.getUsuario().getUsername().equals(username)) {
            throw new ForbiddenException(Constantes.REGISTRO_NO_PERTENECE);
        }
        return mapper.toDomain(entity);
    }

    public RegistroEntrenamiento save(RegistroEntrenamientoDTO dto, String
            username) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new BadRequestException(Constantes.USUARIO_NO_ENCONTRADO +
                    username);
        }

        RegistroEntrenamientoEntity entity = new RegistroEntrenamientoEntity();
        entity.setUsuario(usuario);
        entity.setFecha(dto.fecha());
        entity.setObservaciones(dto.observaciones());

        if (dto.rutinaId() != null) {

            rutinaRepository.findById(dto.rutinaId()).ifPresent(entity::setRutina);
        }

        List<RegistroDetalleEntity> detalles = new ArrayList<>();
        if (dto.detalles() != null) {
            for (RegistroDetalleDTO detalleDTO : dto.detalles()) {
                EjercicioEntity ejercicio =
                        ejercicioRepository.findById(detalleDTO.ejercicioId())
                                .orElseThrow(() -> new
                                        BadRequestException(Constantes.EJERCICIO_NO_ENCONTRADO));
                RegistroDetalleEntity detalle = new RegistroDetalleEntity();
                detalle.setRegistro(entity);
                detalle.setEjercicio(ejercicio);
                detalle.setSeries(detalleDTO.series());
                detalle.setRepeticiones(detalleDTO.repeticiones());
                detalle.setPeso(detalleDTO.peso());
                detalle.setDuracionMinutos(detalleDTO.duracionMinutos());
                detalle.setVelocidad(detalleDTO.velocidad());
                detalle.setInclinacion(detalleDTO.inclinacion());
                detalle.setKcalGastadas(detalleDTO.kcalGastadas());
                detalles.add(detalle);
            }
        }
        entity.setDetalles(detalles);

        return mapper.toDomain(registroRepository.save(entity));
    }

    public void delete(Long id, String username) {
        RegistroEntrenamientoEntity entity = registroRepository.findById(id)
                .orElseThrow(() -> new
                        BadRequestException(Constantes.REGISTRO_NO_ENCONTRADO));
        if (!entity.getUsuario().getUsername().equals(username)) {
            throw new ForbiddenException(Constantes.REGISTRO_NO_PERTENECE);
        }
        registroRepository.deleteById(id);
    }
}
