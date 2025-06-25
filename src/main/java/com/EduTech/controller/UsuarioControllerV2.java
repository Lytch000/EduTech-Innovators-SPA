package com.EduTech.controller;

import com.EduTech.assemblers.UsuariosAssembler;
import com.EduTech.dto.user.LoginRequest;
import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/usuarios")
public class UsuarioControllerV2 {

    private final UsuarioService service;
    private final UsuariosAssembler assembler;

    public UsuarioControllerV2(UsuarioService service, UsuariosAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    /**
     * Login de usuario con HATEOAS.
     * @param request DTO con email y password.
     * @return Usuario autenticado envuelto en EntityModel o error de credenciales.
     */
    @Operation(
        summary = "Login de usuario",
        description = "Permite autenticar un usuario con email y contraseña. Devuelve el usuario autenticado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UsuarioDTO usuario = service.login(request.getEmail(), request.getPassword());
            EntityModel<UsuarioDTO> usuarioModel = assembler.toModel(usuario);
            return ResponseEntity.ok(usuarioModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

}
