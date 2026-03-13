package com.powertrack.backend.data;

import com.powertrack.backend

.data.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UsuarioEntity findByCodigoActivacion(String codigoActivacion);
}
