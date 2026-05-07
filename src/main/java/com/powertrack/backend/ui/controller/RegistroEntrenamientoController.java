package com.powertrack.backend.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.RegistroDetalle;
import com.powertrack.backend.domain.model.RegistroEntrenamiento;
import com.powertrack.backend.domain.service.RegistroEntrenamientoService;
import com.powertrack.backend.ui.dto.*;
import com.powertrack.backend.ui.security.IsUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constantes.API_REGISTROS)
@Tag(name = Constantes.TAG_REGISTROS, description = Constantes.TAG_REGISTROS_DESC)
public class RegistroEntrenamientoController {

    private final RegistroEntrenamientoService registroService;

    public RegistroEntrenamientoController(RegistroEntrenamientoService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    @IsUser
    @Operation(summary = "Ver historial de entrenamientos del usuario autenticado")
    public ResponseEntity<List<RegistroEntrenamientoResponseDTO>> getAll(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(registroService.getAllByUsuario(userDetails.getUsername())
                .stream().map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @IsUser
    @Operation(summary = "Ver detalle de un registro")
    public ResponseEntity<RegistroEntrenamientoResponseDTO> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(toDTO(registroService.getById(id, userDetails.getUsername())));
    }

    @PostMapping
    @IsUser
    @Operation(summary = "Registrar nueva sesión de entrenamiento")
    public ResponseEntity<RegistroEntrenamientoResponseDTO> save(
            @RequestBody RegistroEntrenamientoDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDTO(registroService.save(request, userDetails.getUsername())));
    }

    @DeleteMapping("/{id}")
    @IsUser
    @Operation(summary = "Eliminar registro de entrenamiento")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        registroService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    private RegistroEntrenamientoResponseDTO toDTO(RegistroEntrenamiento r) {
        List<RegistroDetalleResponseDTO> detalles = r.detalles().stream()
                .map(this::detalleToDTO)
                .collect(Collectors.toList());
        return new RegistroEntrenamientoResponseDTO(r.id(), r.rutinaId(), r.fecha(), r.observaciones(), detalles);
    }

    private RegistroDetalleResponseDTO detalleToDTO(RegistroDetalle d) {
        EjercicioResponseDTO eDTO = new EjercicioResponseDTO(d.ejercicio().id(), d.ejercicio().nombre(),
                d.ejercicio().tipoEntrenamiento(), d.ejercicio().imagenUrl(), d.ejercicio().descripcion());
        return new RegistroDetalleResponseDTO(d.id(), eDTO, d.series(), d.repeticiones(), d.peso(), d.duracionMinutos(), d.velocidad(), d.inclinacion(), d.kcalGastadas());
    }
}