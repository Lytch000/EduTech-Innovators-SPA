package com.EduTech.controller;

import com.EduTech.assemblers.RolesAssembler;
import com.EduTech.dto.MensajeDTO;
import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.service.RolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Roles V2", description = "Operaciones HATEOAS de roles")
@RestController
@RequestMapping("api/v2/roles")
public class RolesControllerV2 {

    private final RolesService rolesService;
    private final RolesAssembler assembler;

    public RolesControllerV2(RolesService rolesService, RolesAssembler assembler) {
        this.rolesService = rolesService;
        this.assembler = assembler;
    }

    /**
     * Obtiene todos los roles con enlaces HATEOAS.
     * @return Lista de roles o 204 si no hay datos.
     */
    @Operation(
        summary = "Obtener todos los roles",
        description = "Devuelve una colección de roles con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de roles encontrada"),
            @ApiResponse(responseCode = "204", description = "No hay roles registrados")
        }
    )
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RolesDTO>>> listar() {
        List<RolesDTO> roles = rolesService.listar();

        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<RolesDTO>> roleModels = roles.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(roleModels,
                        linkTo(methodOn(RolesControllerV2.class).listar()).withSelfRel())
        );
    }

    /**
     * Crea un nuevo rol.
     * @param rol Datos del nuevo rol.
     * @return Rol creado o error de validación.
     */
    @Operation(
        summary = "Crear un nuevo rol",
        description = "Permite crear un nuevo rol en el sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rol creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o campos vacíos"),
            @ApiResponse(responseCode = "409", description = "El rol ya existe")
        }
    )
    @PostMapping
    public ResponseEntity<?> addNewRol(@RequestBody Roles rol) {
        try {
            Roles nuevoRol = rolesService.addNewRol(rol);
            RolesDTO dto = new RolesDTO(nuevoRol);
            return ResponseEntity.ok(assembler.toModel(dto));
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

    /**
     * Elimina un rol por su ID.
     * @param id ID del rol a eliminar.
     * @return Mensaje de éxito.
     */
    @Operation(
        summary = "Eliminar un rol",
        description = "Elimina un rol existente por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado exitosamente")
        }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable Long id) {
        String mensaje = rolesService.deleteRol(id);

        MensajeDTO mensajeDTO = new MensajeDTO(mensaje);

        if ("No se encuentra rol especificado".equals(mensaje)) {
            EntityModel<MensajeDTO> errorModel = EntityModel.of(
                mensajeDTO,
                linkTo(methodOn(RolesControllerV2.class).listar()).withRel("roles")
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorModel);
        }

        EntityModel<MensajeDTO> model = EntityModel.of(
            mensajeDTO,
            linkTo(methodOn(RolesControllerV2.class).listar()).withRel("roles"),
            linkTo(methodOn(RolesControllerV2.class).addNewRol(null)).withRel("crear")
        );
        return ResponseEntity.ok(model);
    }

    /**
     * Actualiza un rol existente.
     * @param id ID del rol a actualizar.
     * @param rol Datos actualizados del rol.
     * @return Mensaje de éxito o error si no se encuentra el rol.
     */
    @Operation(
        summary = "Actualizar un rol",
        description = "Actualiza los datos de un rol existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encuentra el rol indicado")
        }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRol(@PathVariable Long id, @RequestBody Roles rol) {
        String resultado = rolesService.updateRol(id, rol);
        MensajeDTO mensajeDTO = new MensajeDTO(resultado);

        if ("No se encuentra rol indicado".equals(resultado)) {
            EntityModel<MensajeDTO> errorModel = EntityModel.of(
                mensajeDTO,
                linkTo(methodOn(RolesControllerV2.class).listar()).withRel("roles")
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorModel);
        }

        EntityModel<MensajeDTO> model = EntityModel.of(
            mensajeDTO,
            linkTo(methodOn(RolesControllerV2.class).listar()).withRel("roles"),
            linkTo(methodOn(RolesControllerV2.class).addNewRol(null)).withRel("crear")
        );
        return ResponseEntity.ok(model);
    }
}
