package com.powertrack.backend.domain.service;


import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.UsuarioRepository;
import com.powertrack.backend.data.entity.UsuarioEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.UsuarioMapper;
import com.powertrack.backend.domain.model.Rol;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.ui.dto.PerfilDTO;
import com.powertrack.backend.ui.dto.UsuarioDTO;
import com.powertrack.backend.ui.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final EmailService emailService;
    private final RecomendacionService recomendacionService;
    private final TextoPersonalizadoService textoService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          UsuarioMapper usuarioMapper, EmailService emailService,
                          RecomendacionService recomendacionService,
                          TextoPersonalizadoService textoService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
        this.emailService = emailService;
        this.recomendacionService = recomendacionService;
        this.textoService = textoService;
    }

    public Usuario register(UsuarioDTO request) {
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new BadRequestException(Constantes.MSG_USERNAME_YA_EN_USO);
        }
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BadRequestException(Constantes.MSG_EMAIL_YA_EN_USO);
        }

        String codigoActivacion = UUID.randomUUID().toString();
        LocalDateTime expiracionCodigo = LocalDateTime.now().plusHours(48);

        UsuarioEntity entity = new UsuarioEntity();
        entity.setUsername(request.username());
        entity.setPassword(passwordEncoder.encode(request.password()));
        entity.setEmail(request.email());
        entity.setNombre(request.nombre());
        entity.setRol(Rol.USER);
        entity.setActivo(false);
        entity.setCodigoActivacion(codigoActivacion);
        entity.setExpiracionCodigo(expiracionCodigo);
        entity.setFormularioCompletado(false);

        UsuarioEntity guardado = usuarioRepository.save(entity);
        emailService.enviarEmailActivacion(guardado.getEmail(),
                guardado.getNombre(), codigoActivacion);

        return usuarioMapper.toDomain(guardado);
    }

    public Usuario activarCuenta(String codigoActivacion) {
        UsuarioEntity entity =
                usuarioRepository.findByCodigoActivacion(codigoActivacion);
        if (entity == null) {
            throw new BadRequestException(Constantes.CODIGO_INVALIDO);
        }
        if (entity.getExpiracionCodigo() != null &&
                entity.getExpiracionCodigo().isBefore(LocalDateTime.now())) {
            throw new BadRequestException(Constantes.CODIGO_EXPIRADO);
        }

        entity.setActivo(true);
        entity.setCodigoActivacion(null);
        entity.setExpiracionCodigo(null);

        return usuarioMapper.toDomain(usuarioRepository.save(entity));
    }

    public Usuario completarPerfil(String username, PerfilDTO perfil) {
        UsuarioEntity entity = usuarioRepository.findByUsername(username);
        if (entity == null) {
            throw new BadRequestException(Constantes.USUARIO_NO_ENCONTRADO + username);
        }

        entity.setGenero(perfil.genero());
        entity.setEdad(perfil.edad());
        entity.setObjetivo(perfil.objetivo());
        entity.setNivel(perfil.nivel());
        entity.setDiasEntrenamiento(perfil.diasEntrenamiento());
        entity.setLesion(perfil.lesion());
        entity.setPreferencia(perfil.preferencia());
        entity.setPesoCat(perfil.pesoCat());
        entity.setRecomendacion(recomendacionService.calcular(perfil));
        entity.setFormularioCompletado(true);

        int objetivo = perfil.objetivo() != null ? perfil.objetivo() : 0;
        int nivel = perfil.nivel() != null ? perfil.nivel() : 0;
        int dias = perfil.diasEntrenamiento() != null ? perfil.diasEntrenamiento() : 3;
        int lesion = perfil.lesion() != null ? perfil.lesion() : 0;
        int genero = perfil.genero() != null ? perfil.genero() : 0;
        int edad = perfil.edad() != null ? perfil.edad() : 0;
        int pesoCat = perfil.pesoCat() != null ? perfil.pesoCat() : 1;

        entity.setDescripcionRutina(textoService.generarDescripcionRutina(objetivo, nivel, dias, lesion));
        entity.setConsejosNutricion(textoService.generarConsejosNutricion(objetivo, genero, edad, pesoCat));

        return usuarioMapper.toDomain(usuarioRepository.save(entity));
    }

    public Usuario getByUsername(String username) {
        UsuarioEntity entity = usuarioRepository.findByUsername(username);
        if (entity == null) {
            throw new BadRequestException(Constantes.USUARIO_NO_ENCONTRADO + username);
        }
        if (entity.isFormularioCompletado() && entity.getDescripcionRutina() == null) {
            entity.setDescripcionRutina(textoService.generarDescripcionRutina(
                    entity.getObjetivo() != null ? entity.getObjetivo() : 0,
                    entity.getNivel() != null ? entity.getNivel() : 0,
                    entity.getDiasEntrenamiento() != null ? entity.getDiasEntrenamiento() : 3,
                    entity.getLesion() != null ? entity.getLesion() : 0
            ));
            entity.setConsejosNutricion(textoService.generarConsejosNutricion(
                    entity.getObjetivo() != null ? entity.getObjetivo() : 0,
                    entity.getGenero() != null ? entity.getGenero() : 0,
                    entity.getEdad() != null ? entity.getEdad() : 0,
                    entity.getPesoCat() != null ? entity.getPesoCat() : 1
            ));
            usuarioRepository.save(entity);
        }
        return usuarioMapper.toDomain(entity);
    }


}
