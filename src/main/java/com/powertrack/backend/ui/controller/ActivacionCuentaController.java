package com.powertrack.backend.ui.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.powertrack.backend.common.Constantes;
import com.powertrack.backend.domain.model.Usuario;
import com.powertrack.backend.ui.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(Constantes.API_ACTIVAR)
@Tag(name = Constantes.TAG_ACTIVACION, description = Constantes.TAG_ACTIVACION_DESC)
public class ActivacionCuentaController {
    private final AuthService authService;

    public ActivacionCuentaController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping
    @Operation(summary = "Activar cuenta de usuario")
    public String activar(@RequestParam(Constantes.CODIGO) String
                                  codigoActivacion, Model model) {
        Usuario usuario = authService.activarCuenta(codigoActivacion);
        model.addAttribute(Constantes.NOMBRE_USUARIO, usuario.nombre());
        return Constantes.TEMPLATE;
    }
}

