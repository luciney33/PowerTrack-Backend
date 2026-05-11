package com.powertrack.backend.ui.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.Rutina;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.domain.service.RutinaService;
import com.powertrack.backend.domain.service.UsuarioService;
import com.powertrack.backend.ui.dto.RutinaEjercicioResponseDTO;
import com.powertrack.backend.ui.dto.RutinaResponseDTO;
import com.powertrack.backend.ui.security.IsAdmin;
import com.powertrack.backend.ui.security.IsUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constantes.API_RUTINAS)
@Tag(name = Constantes.TAG_RUTINAS, description = Constantes.TAG_RUTINAS_DESC)
public class RutinaController {

    private final RutinaService rutinaService;
    private final UsuarioService usuarioService;

    public RutinaController(RutinaService rutinaService, UsuarioService usuarioService) {
        this.rutinaService = rutinaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @IsUser
    @Operation(summary = "Listar todas las rutinas")
    public ResponseEntity<List<RutinaResponseDTO>> getAll() {
        return ResponseEntity.ok(rutinaService.getAll().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @IsUser
    @Operation(summary = "Obtener rutina por ID")
    public ResponseEntity<RutinaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(rutinaService.getById(id)));
    }

    @GetMapping(Constantes.RECOMENDADA)
    @IsUser
    @Operation(summary = "Obtener rutina recomendada para el usuario autenticado")
    public ResponseEntity<RutinaResponseDTO> getRecomendada(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.getByUsername(userDetails.getUsername());
        if (!usuario.formularioCompletado() || usuario.recomendacion() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(toDTO(rutinaService.getByTipo(usuario.recomendacion())));
    }

    @PostMapping
    @IsAdmin
    @Operation(summary = "Crear rutina (solo admin)")
    public ResponseEntity<RutinaResponseDTO> save(@RequestBody RutinaResponseDTO request) {
        Rutina nueva = new Rutina(null, request.nombre(), request.descripcion(), request.tipo(), List.of());
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(rutinaService.save(nueva)));
    }

    @PutMapping("/{id}")
    @IsAdmin
    @Operation(summary = "Actualizar rutina (solo admin)")
    public ResponseEntity<RutinaResponseDTO> update(@PathVariable Long id,
                                                    @RequestBody RutinaResponseDTO request) {
        Rutina actualizada = new Rutina(id, request.nombre(), request.descripcion(), request.tipo(), List.of());
        return ResponseEntity.ok(toDTO(rutinaService.update(id, actualizada)));
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    @Operation(summary = "Eliminar rutina (solo admin)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rutinaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private RutinaResponseDTO toDTO(Rutina r) {
        List<RutinaEjercicioResponseDTO> ejercicios = r.ejercicios().stream()
                .map(e -> new RutinaEjercicioResponseDTO(e.ejercicioId(), e.nombre(),
                        e.tipoEntrenamiento(), e.imagenUrl(), e.descripcion(),
                        e.series(), e.repeticiones(), e.descansoSeg()))
                .collect(Collectors.toList());
        return new RutinaResponseDTO(r.id(), r.nombre(), r.descripcion(), r.tipo(), ejercicios);
    }
}