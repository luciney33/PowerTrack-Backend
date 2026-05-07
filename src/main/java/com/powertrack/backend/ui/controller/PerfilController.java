package com.powertrack.backend.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.domain.service.UsuarioService;
import com.powertrack.backend.ui.dto.PerfilDTO;
import com.powertrack.backend.ui.dto.UsuarioResponseDTO;
import com.powertrack.backend.ui.security.IsUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.API_PERFIL)
@Tag(name = Constantes.TAG_PERFIL, description = Constantes.TAG_PERFIL_DESC)
public class PerfilController {

    private final UsuarioService usuarioService;

    public PerfilController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @IsUser
    @Operation(summary = "Ver perfil del usuario autenticado")
    public ResponseEntity<UsuarioResponseDTO> getPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario =
                usuarioService.getByUsername(userDetails.getUsername());
        return ResponseEntity.ok(toResponseDTO(usuario));
    }

    @PostMapping(Constantes.COMPLETAR)
    @IsUser
    @Operation(summary = "Completar formulario de perfil fitness",
            description = "Guarda las 7 variables del perfil y calcula la recomendación personalizada")
    public ResponseEntity<UsuarioResponseDTO> completarPerfil(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PerfilDTO perfil) {
        Usuario usuario =
                usuarioService.completarPerfil(userDetails.getUsername(), perfil);
        return ResponseEntity.ok(toResponseDTO(usuario));
    }

    private UsuarioResponseDTO toResponseDTO(Usuario u) {
        return new UsuarioResponseDTO(u.id(), u.username(), u.email(),
                u.nombre(),
                u.rol(), u.formularioCompletado(), u.recomendacion(), u.descripcionRutina(), u.consejosNutricion());
    }
}
