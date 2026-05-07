package com.powertrack.backend.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.ui.dto.*;
import com.powertrack.backend.ui.security.IsUser;
import com.powertrack.backend.ui.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(Constantes.API_AUTH)
@Tag(name = Constantes.TAG_AUTH, description = Constantes.TAG_AUTH_DESC)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(Constantes.AUTH_LOGIN)
    @Operation(summary = "Iniciar sesión")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)
    {
        Usuario usuario = authService.login(request.username(),
                request.password());
        JwtTokenPair tokens = authService.generateTokens(usuario);
        return ResponseEntity.ok(new LoginResponse(
                tokens.accessToken(), tokens.refreshToken(),
                toResponseDTO(usuario), Constantes.MSG_LOGIN_SUCCESS));
    }

    @PostMapping(Constantes.AUTH_REGISTER)
    @Operation(summary = "Registrar nuevo usuario")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody UsuarioDTO request) {
        Usuario usuario = authService.register(request);
        return
                ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(usuario));
    }

    @PostMapping(Constantes.REFRESH_ENDPOINT)
    @Operation(summary = "Refrescar access token")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshTokenRequest request) {
        JwtTokenPair tokens =
                authService.refreshAccessToken(request.refreshToken(), request.accessToken());
        Usuario usuario = authService.getUserFromToken(tokens.accessToken());
        return ResponseEntity.ok(new LoginResponse(
                tokens.accessToken(), tokens.refreshToken(),
                toResponseDTO(usuario), Constantes.MSG_TOKEN_REFRESCADO));
    }

    @PostMapping(Constantes.AUTH_LOGOUT)
    @IsUser
    @Operation(summary = "Cerrar sesión")
    public ResponseEntity<ApiSuccessResponse> logout(HttpServletRequest request)
    {
        return ResponseEntity.ok(new ApiSuccessResponse(true,
                Constantes.MSG_LOGOUT_SUCCESS));
    }

    private UsuarioResponseDTO toResponseDTO(Usuario u) {
        return new UsuarioResponseDTO(u.id(), u.username(), u.email(),
                u.nombre(), u.rol(), u.formularioCompletado(), u.recomendacion(), u.descripcionRutina(), u.consejosNutricion());
    }
}