package com.powertrack.backend.domain.mapper;


import com.powertrack.backend

.data.entity.UsuarioEntity;
import com.powertrack.backend

.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return new Usuario(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getNombre(),
                entity.getRol(),
                entity.isActivo(),
                entity.getCodigoActivacion(),
                entity.getExpiracionCodigo(),
                entity.getGenero(),
                entity.getEdad(),
                entity.getObjetivo(),
                entity.getNivel(),
                entity.getDiasEntrenamiento(),
                entity.getLesion(),
                entity.getPreferencia(),
                entity.getRecomendacion(),
                entity.isFormularioCompletado()
        );
    }

    public UsuarioEntity toEntity(Usuario u) {
        if (u == null) return null;
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(u.id());
        entity.setUsername(u.username());
        entity.setPassword(u.password());
        entity.setEmail(u.email());
        entity.setNombre(u.nombre());
        entity.setRol(u.rol());
        entity.setActivo(u.activo());
        entity.setCodigoActivacion(u.codigoActivacion());
        entity.setExpiracionCodigo(u.expiracionCodigo());
        entity.setGenero(u.genero());
        entity.setEdad(u.edad());
        entity.setObjetivo(u.objetivo());
        entity.setNivel(u.nivel());
        entity.setDiasEntrenamiento(u.diasEntrenamiento());
        entity.setLesion(u.lesion());
        entity.setPreferencia(u.preferencia());
        entity.setRecomendacion(u.recomendacion());
        entity.setFormularioCompletado(u.formularioCompletado());
        return entity;
    }
}
