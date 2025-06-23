package com.EduTech.controller;

import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.repository.RolesRepository;
import com.EduTech.service.RolesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Roles", description = "Operaciones relacionadas con la gestión de roles")
@RestController
@RequestMapping("api/v1/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private RolesRepository rolesRepository;

    @Operation(
        summary = "Listar todos los roles",
        description = "Obtiene una lista de todos los roles registrados en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de roles obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RolesDTO.class)),
                examples = @ExampleObject(
                    value = "[{\"id\":1,\"nombre\":\"ADMIN\"},{\"id\":2,\"nombre\":\"USER\"}]"
                )
            )
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No hay roles registrados",
            content = @Content
        )
    })
    @GetMapping
    public ResponseEntity<List<RolesDTO>> listar(){
        List<RolesDTO> roles = rolesService.listar();
        
        if(roles.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(roles);
    }

    @Operation(
        summary = "Crear un nuevo rol",
        description = "Agrega un nuevo rol al sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Rol creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Roles.class),
                examples = @ExampleObject(
                    value = "{\"id\":3,\"nombre\":\"EDITOR\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(
                    value = "El nombre del rol no puede estar vacío"
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "El rol ya existe",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(
                    value = "El rol ya existe"
                )
            )
        )
    })
    @PostMapping()
    public ResponseEntity<?> addNewRol(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto rol a crear",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Roles.class),
                examples = @ExampleObject(
                    value = "{\"nombre\":\"EDITOR\"}"
                )
            )
        )
        @RequestBody Roles rol
    ){
        try {
            Roles nuevoRol = rolesService.addNewRol(rol);
            return ResponseEntity.ok(nuevoRol);
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (msg.contains("vacío") || msg.contains("vacía")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            } else if (msg.contains("ya existe")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @Operation(
        summary = "Eliminar un rol",
        description = "Elimina un rol existente por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Rol eliminado exitosamente",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(
                    value = "Rol eliminado correctamente"
                )
            )
        )
    })
    @DeleteMapping("/delete/{id}")
    public String deleteRol(
        @Parameter(
            description = "ID del rol a eliminar",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    ){
        return rolesService.deleteRol(id);
    }

    @Operation(
        summary = "Actualizar un rol",
        description = "Actualiza la información de un rol existente por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Rol actualizado exitosamente",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(
                    value = "Rol actualizado correctamente"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encuentra rol indicado",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(
                    value = "No se encuentra rol indicado"
                )
            )
        )
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRol(
        @Parameter(
            description = "ID del rol a actualizar",
            required = true,
            example = "1"
        )
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto rol con los datos actualizados",
            required = true,
            content = @Content(
                schema = @Schema(implementation = Roles.class),
                examples = @ExampleObject(
                    value = "{\"nombre\":\"NUEVO_NOMBRE\"}"
                )
            )
        )
        @RequestBody Roles rol
    ) {
        String resultado = rolesService.updateRol(id, rol);
        if ("No se encuentra rol indicado".equals(resultado)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

}
