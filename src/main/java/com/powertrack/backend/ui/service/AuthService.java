package com.powertrack.backend.ui.service;

import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.data.UsuarioRepository;
import com.powertrack.backend.data.entity.UsuarioEntity;
import com.powertrack.backend.domain.error.BadCredentialsException;
import com.powertrack.backend.domain.error.UnauthorizedException;
import com.powertrack.backend.domain.mapper.UsuarioMapper;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.domain.service.UsuarioService;
import com.powertrack.backend.ui.dto.JwtTokenPair;
import com.powertrack.backend.ui.dto.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioService usuarioService, UsuarioRepository
                               usuarioRepository,
                       UsuarioMapper usuarioMapper, JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Usuario login(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException e) {
            log.warn(Constantes.FALLO_AUTENTICACION, username);
            throw new BadCredentialsException(Constantes.MSG_LOGIN_INVALID);
        }
        UsuarioEntity entity = usuarioRepository.findByUsername(username);
        if (entity == null) throw new
                BadCredentialsException(Constantes.MSG_LOGIN_INVALID);
        return usuarioMapper.toDomain(entity);
    }

    public Usuario register(UsuarioDTO dto) {
        return usuarioService.register(dto);
    }

    public Usuario activarCuenta(String codigo) {
        return usuarioService.activarCuenta(codigo);
    }

    public JwtTokenPair generateTokens(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constantes.ROL, usuario.rol().toString());
        String accessToken = jwtService.generateToken(claims,
                usuario.username());
        String refreshToken =
                jwtService.generateRefreshToken(usuario.username());
        return new JwtTokenPair(accessToken, refreshToken);
    }

    public JwtTokenPair refreshAccessToken(String refreshToken, String
            oldAccessToken) {
        try {
            String username = jwtService.extractUsername(refreshToken);
            if (!jwtService.isTokenValid(refreshToken, username)) {
                throw new
                        UnauthorizedException(Constantes.REFRESH_TOKEN_INVALIDO_O_EXPIRADO);
            }
            UsuarioEntity entity = usuarioRepository.findByUsername(username);
            if (entity == null) throw new
                    UnauthorizedException(Constantes.USUARIO_NO_ENCONTRADO + username);
            return generateTokens(usuarioMapper.toDomain(entity));
        } catch (Exception e) {
            log.error(Constantes.ERROR_REFRESCAR_TOKENS, e.getMessage());
            throw new UnauthorizedException(Constantes.REFRESH_TOKEN_INVALIDO +
                    e.getMessage());
        }
    }

    public Usuario getUserFromToken(String token) {
        String username = jwtService.extractUsername(token);
        UsuarioEntity entity = usuarioRepository.findByUsername(username);
        if (entity == null) throw new
                UnauthorizedException(Constantes.USUARIO_NO_ENCONTRADO + username);
        return usuarioMapper.toDomain(entity);
    }
}

