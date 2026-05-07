package com.powertrack.backend.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.EjercicioRepository;
import com.powertrack.backend.data.RegistroEntrenamientoRepository;
import com.powertrack.backend.data.RutinaRepository;
import com.powertrack.backend.data.UsuarioRepository;
import com.powertrack.backend.data.entity.EjercicioEntity;
import com.powertrack.backend.data.entity.RegistroEntrenamientoEntity;
import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend.data.entity.UsuarioEntity;
import com.powertrack.backend.domain.error.BadRequestException;
import com.powertrack.backend.domain.error.ForbiddenException;
import com.powertrack.backend.domain.mapper.RegistroEntrenamientoMapper;
import com.powertrack.backend.domain.model.RegistroEntrenamiento;
import com.powertrack.backend.domain.service.RegistroEntrenamientoService;
import com.powertrack.backend.ui.dto.RegistroDetalleDTO;
import com.powertrack.backend.ui.dto.RegistroEntrenamientoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistroEntrenamientoServiceTest {

    @Mock private RegistroEntrenamientoRepository registroRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private RutinaRepository rutinaRepository;
    @Mock private EjercicioRepository ejercicioRepository;
    @Mock private RegistroEntrenamientoMapper mapper;

    @InjectMocks
    private RegistroEntrenamientoService registroService;



    @Test
    void deberiaRetornarRegistrosDelUsuario() {
        RegistroEntrenamientoEntity entity1 = new RegistroEntrenamientoEntity();
        RegistroEntrenamientoEntity entity2 = new RegistroEntrenamientoEntity();
        RegistroEntrenamiento registro1 = new RegistroEntrenamiento(1L, 10L, null, LocalDate.now(), "Obs 1", List.of());
        RegistroEntrenamiento registro2 = new RegistroEntrenamiento(2L, 10L, null, LocalDate.now(), "Obs 2", List.of());

        when(registroRepository.findByUsuarioUsername("lucia")).thenReturn(List.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(registro1);
        when(mapper.toDomain(entity2)).thenReturn(registro2);

        List<RegistroEntrenamiento> resultado = registroService.getAllByUsuario("lucia");

        assertEquals(2, resultado.size());
        assertEquals("Obs 1", resultado.get(0).observaciones());
        assertEquals("Obs 2", resultado.get(1).observaciones());
    }

    @Test
    void deberiaRetornarListaVaciaCuandoUsuarioNoTieneRegistros() {
        when(registroRepository.findByUsuarioUsername("lucia")).thenReturn(List.of());

        List<RegistroEntrenamiento> resultado = registroService.getAllByUsuario("lucia");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }



    @Test
    void deberiaRetornarRegistroCuandoExisteYPerteneceAlUsuario() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername("lucia");
        RegistroEntrenamientoEntity entity = new RegistroEntrenamientoEntity();
        entity.setUsuario(usuario);
        RegistroEntrenamiento registroEsperado = new RegistroEntrenamiento(1L, 10L, null, LocalDate.now(), "Obs", List.of());

        when(registroRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(registroEsperado);

        RegistroEntrenamiento resultado = registroService.getById(1L, "lucia");

        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
    }

    @Test
    void deberiaLanzarExcepcionEnGetByIdCuandoRegistroNoExiste() {
        when(registroRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> registroService.getById(99L, "lucia"));

        assertEquals(Constantes.REGISTRO_NO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void deberiaLanzarForbiddenCuandoRegistroNoPerteneceAlUsuario() {
        UsuarioEntity otraPersona = new UsuarioEntity();
        otraPersona.setUsername("otro");
        RegistroEntrenamientoEntity entity = new RegistroEntrenamientoEntity();
        entity.setUsuario(otraPersona);

        when(registroRepository.findById(1L)).thenReturn(Optional.of(entity));

        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> registroService.getById(1L, "lucia"));

        assertEquals(Constantes.REGISTRO_NO_PERTENECE, ex.getMessage());
        verify(mapper, never()).toDomain(any());
    }



    @Test
    void deberiaGuardarRegistroConRutinaYDetalles() {
        RegistroDetalleDTO detalleDTO = new RegistroDetalleDTO(1L, 3, 10, 50.0, null, null, null, null);
        RegistroEntrenamientoDTO dto = new RegistroEntrenamientoDTO(5L, LocalDate.now(), "Sesión dura", List.of(detalleDTO));
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername("lucia");
        RutinaEntity rutina = new RutinaEntity();
        EjercicioEntity ejercicio = new EjercicioEntity();
        RegistroEntrenamientoEntity entidadGuardada = new RegistroEntrenamientoEntity();
        RegistroEntrenamiento registroEsperado = new RegistroEntrenamiento(1L, 10L, 5L, LocalDate.now(), "Sesión dura", List.of());

        when(usuarioRepository.findByUsername("lucia")).thenReturn(usuario);
        when(rutinaRepository.findById(5L)).thenReturn(Optional.of(rutina));
        when(ejercicioRepository.findById(1L)).thenReturn(Optional.of(ejercicio));
        when(registroRepository.save(any(RegistroEntrenamientoEntity.class))).thenReturn(entidadGuardada);
        when(mapper.toDomain(entidadGuardada)).thenReturn(registroEsperado);

        RegistroEntrenamiento resultado = registroService.save(dto, "lucia");

        assertNotNull(resultado);
        verify(rutinaRepository).findById(5L);
        verify(ejercicioRepository).findById(1L);
        verify(registroRepository).save(any(RegistroEntrenamientoEntity.class));
    }

    @Test
    void deberiaGuardarRegistroSinRutina() {
        RegistroEntrenamientoDTO dto = new RegistroEntrenamientoDTO(null, LocalDate.now(), "Sin rutina", List.of());
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername("lucia");
        RegistroEntrenamientoEntity entidadGuardada = new RegistroEntrenamientoEntity();
        RegistroEntrenamiento registroEsperado = new RegistroEntrenamiento(1L, 10L, null, LocalDate.now(), "Sin rutina", List.of());

        when(usuarioRepository.findByUsername("lucia")).thenReturn(usuario);
        when(registroRepository.save(any(RegistroEntrenamientoEntity.class))).thenReturn(entidadGuardada);
        when(mapper.toDomain(entidadGuardada)).thenReturn(registroEsperado);

        RegistroEntrenamiento resultado = registroService.save(dto, "lucia");

        assertNotNull(resultado);
        verify(rutinaRepository, never()).findById(any());
    }

    @Test
    void deberiaGuardarRegistroSinDetalles() {
        RegistroEntrenamientoDTO dto = new RegistroEntrenamientoDTO(null, LocalDate.now(), "Sin detalles", null);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername("lucia");
        RegistroEntrenamientoEntity entidadGuardada = new RegistroEntrenamientoEntity();
        RegistroEntrenamiento registroEsperado = new RegistroEntrenamiento(1L, 10L, null, LocalDate.now(), "Sin detalles", List.of());

        when(usuarioRepository.findByUsername("lucia")).thenReturn(usuario);
        when(registroRepository.save(any(RegistroEntrenamientoEntity.class))).thenReturn(entidadGuardada);
        when(mapper.toDomain(entidadGuardada)).thenReturn(registroEsperado);

        RegistroEntrenamiento resultado = registroService.save(dto, "lucia");

        assertNotNull(resultado);
        verify(ejercicioRepository, never()).findById(any());
    }

    @Test
    void deberiaLanzarExcepcionAlGuardarSiUsuarioNoExiste() {
        RegistroEntrenamientoDTO dto = new RegistroEntrenamientoDTO(null, LocalDate.now(), "Obs", List.of());

        when(usuarioRepository.findByUsername("noexiste")).thenReturn(null);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> registroService.save(dto, "noexiste"));

        assertTrue(ex.getMessage().contains(Constantes.USUARIO_NO_ENCONTRADO));
        verify(registroRepository, never()).save(any());
    }

    @Test
    void deberiaLanzarExcepcionAlGuardarSiEjercicioNoExiste() {
        RegistroDetalleDTO detalleDTO = new RegistroDetalleDTO(99L, 3, 10, 50.0, null, null, null, null);
        RegistroEntrenamientoDTO dto = new RegistroEntrenamientoDTO(null, LocalDate.now(), "Obs", List.of(detalleDTO));
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername("lucia");

        when(usuarioRepository.findByUsername("lucia")).thenReturn(usuario);
        when(ejercicioRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> registroService.save(dto, "lucia"));

        assertEquals(Constantes.EJERCICIO_NO_ENCONTRADO, ex.getMessage());
        verify(registroRepository, never()).save(any());
    }



    @Test
    void deberiaEliminarRegistroCuandoExisteYPerteneceAlUsuario() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername("lucia");
        RegistroEntrenamientoEntity entity = new RegistroEntrenamientoEntity();
        entity.setUsuario(usuario);

        when(registroRepository.findById(1L)).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> registroService.delete(1L, "lucia"));

        verify(registroRepository).deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionAlEliminarSiRegistroNoExiste() {
        when(registroRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> registroService.delete(99L, "lucia"));

        assertEquals(Constantes.REGISTRO_NO_ENCONTRADO, ex.getMessage());
        verify(registroRepository, never()).deleteById(any());
    }

    @Test
    void deberiaLanzarForbiddenAlEliminarSiRegistroNoPerteneceAlUsuario() {
        UsuarioEntity otraPersona = new UsuarioEntity();
        otraPersona.setUsername("otro");
        RegistroEntrenamientoEntity entity = new RegistroEntrenamientoEntity();
        entity.setUsuario(otraPersona);

        when(registroRepository.findById(1L)).thenReturn(Optional.of(entity));

        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> registroService.delete(1L, "lucia"));

        assertEquals(Constantes.REGISTRO_NO_PERTENECE, ex.getMessage());
        verify(registroRepository, never()).deleteById(any());
    }
}
