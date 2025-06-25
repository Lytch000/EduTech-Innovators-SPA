package com.EduTech.controller;

import com.EduTech.assemblers.RolesModelAssembler;
import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.service.RolesService;
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
@RequestMapping("/api/v2/roles")
public class RolesControllerV2 {

    private final RolesService rolesService;
    private final RolesModelAssembler assembler;

    public RolesControllerV2(RolesService rolesService, RolesModelAssembler assembler) {
        this.rolesService = rolesService;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RolesDTO>>> getAllRoles() {
        List<RolesDTO> roles = rolesService.listar();

        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<RolesDTO>> roleModels = roles.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(roleModels,
                        linkTo(methodOn(RolesControllerV2.class).getAllRoles()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public EntityModel<RolesDTO> getRolById(@PathVariable Long id) {
        RolesDTO rolDTO = rolesService.listar().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        return assembler.toModel(rolDTO);
    }

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRol(@PathVariable Long id) {
        return ResponseEntity.ok(rolesService.deleteRol(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRol(@PathVariable Long id, @RequestBody Roles rol) {
        String resultado = rolesService.updateRol(id, rol);
        if ("No se encuentra rol indicado".equals(resultado)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
