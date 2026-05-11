package com.powertrack.backend.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.PlanNutricional;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.domain.service.PlanNutricionalService;
import com.powertrack.backend.domain.service.UsuarioService;
import com.powertrack.backend.ui.dto.ComidaResponseDTO;
import com.powertrack.backend.ui.dto.PlanNutricionalResponseDTO;
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
@RequestMapping(Constantes.API_PLANES)
@Tag(name = Constantes.TAG_PLANES, description = Constantes.TAG_PLANES_DESC)
public class PlanNutricionalController {

    private final PlanNutricionalService planService;
    private final UsuarioService usuarioService;

    public PlanNutricionalController(PlanNutricionalService planService, UsuarioService usuarioService) {
        this.planService = planService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @IsUser
    @Operation(summary = "Listar todos los planes nutricionales")
    public ResponseEntity<List<PlanNutricionalResponseDTO>> getAll() {
        return ResponseEntity.ok(planService.getAll().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @IsUser
    @Operation(summary = "Obtener plan nutricional por ID")
    public ResponseEntity<PlanNutricionalResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(planService.getById(id)));
    }

    @GetMapping(Constantes.RECOMENDADO)
    @IsUser
    @Operation(summary = "Obtener plan nutricional recomendado para el usuario autenticado")
    public ResponseEntity<PlanNutricionalResponseDTO> getRecomendado(
            @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.getByUsername(userDetails.getUsername());
        if (!usuario.formularioCompletado() || usuario.recomendacion() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(toDTO(planService.getByTipo(usuario.recomendacion())));
    }

    @PostMapping
    @IsAdmin
    @Operation(summary = "Crear plan nutricional (solo admin)")
    public ResponseEntity<PlanNutricionalResponseDTO> save(@RequestBody PlanNutricionalResponseDTO request) {
        PlanNutricional nuevo = new PlanNutricional(null, request.nombre(), request.descripcion(),
                request.tipo(), request.caloriasObjetivo(), request.proteinasObjetivo(),
                request.carbohidratosObjetivo(), request.grasasObjetivo(), List.of());
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(planService.save(nuevo)));
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    @Operation(summary = "Eliminar plan nutricional (solo admin)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PlanNutricionalResponseDTO toDTO(PlanNutricional p) {
        List<ComidaResponseDTO> comidas = p.comidas().stream()
                .map(c -> new ComidaResponseDTO(c.id(), c.nombre(), c.calorias(),
                        c.proteinas(), c.carbohidratos(), c.grasas(), c.categoria(), c.imagenUrl()))
                .collect(Collectors.toList());
        return new PlanNutricionalResponseDTO(p.id(), p.nombre(), p.descripcion(), p.tipo(),
                p.caloriasObjetivo(), p.proteinasObjetivo(), p.carbohidratosObjetivo(),
                p.grasasObjetivo(), comidas);
    }
}
