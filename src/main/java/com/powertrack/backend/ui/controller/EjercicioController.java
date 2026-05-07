package com.powertrack.backend.ui.controller;

import com.powertrack.backend.ui.dto.EjercicioResponseDTO;
import com.powertrack.backend.ui.security.IsAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.Ejercicio;
import com.powertrack.backend.domain.service.EjercicioService;
import com.powertrack.backend.ui.security.IsUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constantes.API_EJERCICIOS)
@Tag(name = Constantes.TAG_EJERCICIOS, description = Constantes.TAG_EJERCICIOS_DESC)
public class EjercicioController {
    private final EjercicioService ejercicioService;

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    @IsUser
    @Operation(summary = "Listar todos los ejercicios")
    public ResponseEntity<List<EjercicioResponseDTO>> getAll() {
        return ResponseEntity.ok(ejercicioService.getAll().stream()
                .map(this::toDTO).collect(Collectors.toList()));
    }

    @GetMapping(Constantes.CATEGORIA_PATH)
    @IsUser
    @Operation(summary = "Listar ejercicios por categoría")
    public ResponseEntity<List<EjercicioResponseDTO>> getByCategoria(
            @PathVariable String categoria) {
        return ResponseEntity.ok(ejercicioService.getByCategoria(categoria)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));
    }


    @GetMapping("/{id}")
    @IsUser
    @Operation(summary = "Obtener ejercicio por ID")
    public ResponseEntity<EjercicioResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toDTO(ejercicioService.getById(id)));
    }


    @PostMapping
    @IsAdmin
    @Operation(summary = "Crear ejercicio (solo admin)")
    public ResponseEntity<EjercicioResponseDTO> save(@RequestBody EjercicioResponseDTO request) {
        Ejercicio nuevo = new Ejercicio(null, request.nombre(), request.tipoEntrenamiento(),
                request.imagenUrl(), request.descripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(ejercicioService.save(nuevo)));
    }

    @PutMapping("/{id}")
    @IsAdmin
    @Operation(summary = "Actualizar ejercicio (solo admin)")
    public ResponseEntity<EjercicioResponseDTO> update(@PathVariable Long id,
            @RequestBody EjercicioResponseDTO request) {
        Ejercicio actualizado = new Ejercicio(id, request.nombre(), request.tipoEntrenamiento(),
                request.imagenUrl(), request.descripcion());
        return ResponseEntity.ok(toDTO(ejercicioService.update(id, actualizado)));
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    @Operation(summary = "Eliminar ejercicio (solo admin)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ejercicioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EjercicioResponseDTO toDTO(Ejercicio e) {
        return new EjercicioResponseDTO(e.id(), e.nombre(), e.tipoEntrenamiento(), e.imagenUrl(), e.descripcion());
    }
}
