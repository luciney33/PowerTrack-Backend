package com.powertrack.backend.ui.controller;

import com.powertrack.backend.ui.security.IsAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.Comida;
import com.powertrack.backend.domain.service.ComidaService;
import com.powertrack.backend.ui.dto.ComidaResponseDTO;
import com.powertrack.backend.ui.security.IsUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constantes.API_COMIDAS)
@Tag(name = Constantes.TAG_COMIDAS, description = Constantes.TAG_COMIDAS_DESC)
public class ComidaController {

    private final ComidaService comidaService;

    public ComidaController(ComidaService comidaService) {
        this.comidaService = comidaService;
    }

    @GetMapping
    @IsUser
    @Operation(summary = "Listar todas las comidas")
    public ResponseEntity<List<ComidaResponseDTO>> getAll() {
        return ResponseEntity.ok(comidaService.getAll().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @IsUser
    @Operation(summary = "Obtener comida por ID")
    public ResponseEntity<ComidaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(comidaService.getById(id)));
    }

    @GetMapping(Constantes.CATEGORIA_PATH)
    @IsUser
    @Operation(summary = "Listar comidas por categoría (DESAYUNO, ALMUERZO, CENA, MERIENDA)")
    public ResponseEntity<List<ComidaResponseDTO>> getByCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(comidaService.getByCategoria(categoria).stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @PostMapping
    @IsAdmin
    @Operation(summary = "Crear comida (solo admin)")
    public ResponseEntity<ComidaResponseDTO> save(@RequestBody ComidaResponseDTO request) {
        Comida nueva = new Comida(null, request.nombre(), request.calorias(), request.proteinas(),
                request.carbohidratos(), request.grasas(), request.categoria());
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(comidaService.save(nueva)));
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    @Operation(summary = "Eliminar comida (solo admin)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comidaService.delete(id);
        return ResponseEntity.noContent().build();
    }


    private ComidaResponseDTO toDTO(Comida c) {
        return new ComidaResponseDTO(c.id(), c.nombre(), c.calorias(),
                c.proteinas(), c.carbohidratos(), c.grasas(), c.categoria());
    }
}
