package com.powertrack.backend.domain.mapper;


import com.powertrack.backend.data.entity.UsuarioEntity;
import com.powertrack.backend.domain.model.Usuario;
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
                entity.getPesoCat(),
                entity.isFormularioCompletado(),
                entity.getDescripcionRutina(),
                entity.getConsejosNutricion()
        );
    }
}
