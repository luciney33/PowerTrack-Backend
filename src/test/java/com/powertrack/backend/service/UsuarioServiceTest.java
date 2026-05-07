package com.powertrack.backend.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.UsuarioRepository;
import com.powertrack.backend.data.entity.UsuarioEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.mapper.UsuarioMapper;
import com.powertrack.backend.domain.model.Rol;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.domain.service.RecomendacionService;
import com.powertrack.backend.domain.service.UsuarioService;
import com.powertrack.backend.ui.dto.PerfilDTO;
import com.powertrack.backend.ui.dto.UsuarioDTO;
import com.powertrack.backend.ui.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UsuarioMapper usuarioMapper;
    @Mock private EmailService emailService;
    @Mock private RecomendacionService recomendacionService;

    @InjectMocks
    private UsuarioService usuarioService;


    @Test
    void deberiaRegistrarUsuarioCuandoDatosCorrectos() {
        UsuarioDTO dto = new UsuarioDTO("lucia", "pass123", "lucia@email.com", "Lucia");
        UsuarioEntity entidadGuardada = new UsuarioEntity();
        entidadGuardada.setUsername("lucia");
        entidadGuardada.setEmail("lucia@email.com");
        entidadGuardada.setNombre("Lucia");
        Usuario usuarioEsperado = new Usuario(1L, "lucia", "encoded", "lucia@email.com",
                "Lucia", Rol.USER, false, null, null,
                null, null, null, null, null, null, null, null, null, false, null, null);

        when(usuarioRepository.existsByUsername("lucia")).thenReturn(false);
        when(usuarioRepository.existsByEmail("lucia@email.com")).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("encoded");
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(entidadGuardada);
        when(usuarioMapper.toDomain(entidadGuardada)).thenReturn(usuarioEsperado);

        Usuario resultado = usuarioService.register(dto);

        assertNotNull(resultado);
        assertEquals("lucia", resultado.username());
        verify(usuarioRepository).save(any(UsuarioEntity.class));
        verify(emailService).enviarEmailActivacion(anyString(), anyString(), anyString());
    }

    @Test
    void deberiaCodificarPasswordAlRegistrar() {
        UsuarioDTO dto = new UsuarioDTO("lucia", "pass123", "lucia@email.com", "Lucia");
        UsuarioEntity entidadGuardada = new UsuarioEntity();
        Usuario usuarioEsperado =  new Usuario(1L, "lucia", "encoded", "lucia@email.com",
                "Lucia", Rol.USER, false, null, null,
                null, null, null, null, null, null, null, null, null, false, null, null);

        when(usuarioRepository.existsByUsername(anyString())).thenReturn(false);
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("encoded");
        when(usuarioRepository.save(any())).thenReturn(entidadGuardada);
        when(usuarioMapper.toDomain(entidadGuardada)).thenReturn(usuarioEsperado);

        usuarioService.register(dto);

        verify(passwordEncoder).encode("pass123");
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsernameYaExiste() {
        UsuarioDTO dto = new UsuarioDTO("lucia", "pass123", "lucia@email.com", "Lucia");

        when(usuarioRepository.existsByUsername("lucia")).thenReturn(true);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.register(dto));

        assertEquals(Constantes.MSG_USERNAME_YA_EN_USO, ex.getMessage());
        verify(usuarioRepository, never()).save(any());
        verify(emailService, never()).enviarEmailActivacion(any(), any(), any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEmailYaExiste() {
        UsuarioDTO dto = new UsuarioDTO("lucia", "pass123", "lucia@email.com", "Lucia");

        when(usuarioRepository.existsByUsername("lucia")).thenReturn(false);
        when(usuarioRepository.existsByEmail("lucia@email.com")).thenReturn(true);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.register(dto));

        assertEquals(Constantes.MSG_EMAIL_YA_EN_USO, ex.getMessage());
        verify(usuarioRepository, never()).save(any());
        verify(emailService, never()).enviarEmailActivacion(any(), any(), any());
    }


    @Test
    void deberiaActivarCuentaCuandoCodigoValido() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setActivo(false);
        entity.setCodigoActivacion("codigo-abc");
        entity.setExpiracionCodigo(LocalDateTime.now().plusHours(1));
        UsuarioEntity guardada = new UsuarioEntity();
        Usuario usuarioEsperado = new Usuario(1L, "lucia", "encoded", "lucia@email.com",
                "Lucia", Rol.USER, true, null, null,
                null, null, null, null, null, null, null, null, null, false, null, null);

        when(usuarioRepository.findByCodigoActivacion("codigo-abc")).thenReturn(entity);
        when(usuarioRepository.save(entity)).thenReturn(guardada);
        when(usuarioMapper.toDomain(guardada)).thenReturn(usuarioEsperado);

        Usuario resultado = usuarioService.activarCuenta("codigo-abc");

        assertNotNull(resultado);
        assertTrue(entity.isActivo());
        assertNull(entity.getCodigoActivacion());
        assertNull(entity.getExpiracionCodigo());
        verify(usuarioRepository).save(entity);
    }

    @Test
    void deberiaActivarCuentaCuandoExpiracionEsNull() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setCodigoActivacion("codigo-sin-expiracion");
        entity.setExpiracionCodigo(null);
        UsuarioEntity guardada = new UsuarioEntity();
        Usuario usuarioEsperado = new Usuario(1L, "lucia", "encoded", "lucia@email.com",
                "Lucia", Rol.USER, true, null, null,
                null, null, null, null, null, null, null, null, null, false, null, null);

        when(usuarioRepository.findByCodigoActivacion("codigo-sin-expiracion")).thenReturn(entity);
        when(usuarioRepository.save(entity)).thenReturn(guardada);
        when(usuarioMapper.toDomain(guardada)).thenReturn(usuarioEsperado);

        assertDoesNotThrow(() -> usuarioService.activarCuenta("codigo-sin-expiracion"));
    }

    @Test
    void deberiaLanzarExcepcionCuandoCodigoNoExiste() {
        when(usuarioRepository.findByCodigoActivacion("codigo-inexistente")).thenReturn(null);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.activarCuenta("codigo-inexistente"));

        assertEquals(Constantes.CODIGO_INVALIDO, ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCodigoExpirado() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setCodigoActivacion("codigo-expirado");
        entity.setExpiracionCodigo(LocalDateTime.now().minusHours(1));

        when(usuarioRepository.findByCodigoActivacion("codigo-expirado")).thenReturn(entity);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.activarCuenta("codigo-expirado"));

        assertEquals(Constantes.CODIGO_EXPIRADO, ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }


    @Test
    void deberiaCompletarPerfilCuandoUsuarioExiste() {
        PerfilDTO perfil = new PerfilDTO(1, 25, 1, 2, 3, null,2, 65);
        UsuarioEntity entity = new UsuarioEntity();
        UsuarioEntity guardada = new UsuarioEntity();
        Usuario usuarioEsperado = new Usuario(1L, "lucia", "encoded", "lucia@email.com",
                "Lucia", Rol.USER, true, null, null,
                1, 25, 1, 2, 3, null, 0, 4, 65, false, null, null);

        when(usuarioRepository.findByUsername("lucia")).thenReturn(entity);
        when(recomendacionService.calcular(perfil)).thenReturn(4);
        when(usuarioRepository.save(entity)).thenReturn(guardada);
        when(usuarioMapper.toDomain(guardada)).thenReturn(usuarioEsperado);

        Usuario resultado = usuarioService.completarPerfil("lucia", perfil);

        assertNotNull(resultado);
        assertTrue(entity.isFormularioCompletado());
        assertEquals(4, entity.getRecomendacion());
        verify(recomendacionService).calcular(perfil);
        verify(usuarioRepository).save(entity);
    }

    @Test
    void deberiaLlamarARecomendacionServiceAlCompletarPerfil() {
        PerfilDTO perfil = new PerfilDTO(null, null, 2, 0, null, null, null,65);
        UsuarioEntity entity = new UsuarioEntity();
        UsuarioEntity guardada = new UsuarioEntity();

        when(usuarioRepository.findByUsername("lucia")).thenReturn(entity);
        when(recomendacionService.calcular(perfil)).thenReturn(5);
        when(usuarioRepository.save(entity)).thenReturn(guardada);
        when(usuarioMapper.toDomain(guardada)).thenReturn(mock(Usuario.class));

        usuarioService.completarPerfil("lucia", perfil);

        verify(recomendacionService).calcular(perfil);
    }

    @Test
    void deberiaLanzarExcepcionAlCompletarPerfilSiUsuarioNoExiste() {
        PerfilDTO perfil = new PerfilDTO(null, null, 1, 0, null, null, null,65);

        when(usuarioRepository.findByUsername("noexiste")).thenReturn(null);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.completarPerfil("noexiste", perfil));

        assertTrue(ex.getMessage().contains(Constantes.USUARIO_NO_ENCONTRADO));
        verify(usuarioRepository, never()).save(any());
    }


    @Test
    void deberiaRetornarUsuarioCuandoExiste() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setUsername("lucia");
        Usuario usuarioEsperado = new Usuario(1L, "lucia", "encoded", "lucia@email.com",
                "Lucia", Rol.USER, true, null, null,
                null, null, null, null, null, null, null, null, null, false, null, null);

        when(usuarioRepository.findByUsername("lucia")).thenReturn(entity);
        when(usuarioMapper.toDomain(entity)).thenReturn(usuarioEsperado);

        Usuario resultado = usuarioService.getByUsername("lucia");

        assertNotNull(resultado);
        assertEquals("lucia", resultado.username());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoExiste() {
        when(usuarioRepository.findByUsername("noexiste")).thenReturn(null);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> usuarioService.getByUsername("noexiste"));

        assertTrue(ex.getMessage().contains(Constantes.USUARIO_NO_ENCONTRADO));
    }
}
