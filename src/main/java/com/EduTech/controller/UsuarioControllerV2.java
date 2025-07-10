package com.EduTech.controller;

import com.EduTech.assemblers.UsuariosAssembler;
import com.EduTech.dto.user.LoginRequest;
import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.EduTech.dto.user.CrearUsuarioDto;
import com.EduTech.dto.user.ActualizarUsuarioDto;
import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v2/usuarios")
public class UsuarioControllerV2 {

    private final UsuarioService service;
    private final UsuariosAssembler assembler;

    public UsuarioControllerV2(UsuarioService service, UsuariosAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(
        summary = "Login de usuario con HATEOAS",
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

    @Operation(
        summary = "Obtener todos los usuarios con HATEOAS",
        description = "Devuelve una lista de todos los usuarios registrados en el sistema con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
        }
    )
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> getAllUsers() {
        List<UsuarioDTO> users = service.getUsers().stream()
            .map(RespuestaUsuarioDto::getId)
            .map(service::getOneUser)
            .map(respuesta -> new UsuarioDTO(respuesta.getId(), respuesta.getFirstName(), respuesta.getLastName(), respuesta.getEmail()))
            .collect(Collectors.toList());
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<UsuarioDTO>> userModels = users.stream().map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(userModels));
    }

    @Operation(
        summary = "Crear nuevo usuario con HATEOAS",
        description = "Crea un nuevo usuario en el sistema con los datos proporcionados y devuelve el usuario creado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o campos requeridos faltantes")
        }
    )
    @PostMapping
    public ResponseEntity<EntityModel<UsuarioDTO>> createUser(@RequestBody CrearUsuarioDto newUser) {
        RespuestaUsuarioDto entity = service.createUser(newUser);

        UsuarioDTO usuarioDTO = new UsuarioDTO(entity);

        EntityModel<UsuarioDTO> usuarioModel = assembler.toModel(usuarioDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModel);
    }

    @Operation(
        summary = "Obtener usuario por ID con HATEOAS",
        description = "Devuelve la información de un usuario específico por su ID con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getOneUser(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable Long id) {
        try {
            RespuestaUsuarioDto entity = service.getOneUser(id);
            UsuarioDTO usuarioDTO = new UsuarioDTO(entity);
            EntityModel<UsuarioDTO> usuarioModel = assembler.toModel(usuarioDTO);
            return ResponseEntity.ok(usuarioModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @Operation(
        summary = "Actualizar usuario con HATEOAS",
        description = "Actualiza la información de un usuario existente y devuelve el usuario actualizado con enlaces HATEOAS. Solo se actualizan los campos proporcionados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o validación fallida"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateUser(
        @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
        @PathVariable Long id, 
        @RequestBody ActualizarUsuarioDto userFields) {
        try {
            RespuestaUsuarioDto entity = service.updateUser(id, userFields);
            UsuarioDTO usuarioDTO = new UsuarioDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail());
            EntityModel<UsuarioDTO> usuarioModel = assembler.toModel(usuarioDTO);
            return ResponseEntity.ok(usuarioModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina permanentemente un usuario del sistema por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(
        @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
        @PathVariable Long id) {
        try {
            String message = service.deleteUser(id);
            return ResponseEntity.ok(message);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Obtener detalle de profesor con cursos asignados y HATEOAS",
        description = "Devuelve la información del profesor y la lista de cursos que tiene asignados con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Detalle del profesor obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado o no es un profesor")
        }
    )
    @GetMapping("/profesor/detalle/{id}")
    public ResponseEntity<?> obtenerDetalleProfesor(
        @Parameter(description = "ID del profesor", required = true, example = "1")
        @PathVariable Long id) {
        try {
            ProfesorDetalleCursoDTO detalle = service.obtenerDetalleProfesorConCursos(id);
            EntityModel<ProfesorDetalleCursoDTO> detalleModel = EntityModel.of(detalle);
            return ResponseEntity.ok(detalleModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
