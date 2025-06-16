package com.EduTech.controller;

import com.EduTech.dto.roles.RolRequest;
import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.repository.RolesRepository;
import com.EduTech.service.RolesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping
    public ResponseEntity<List<RolesDTO>> listar(){
        List<RolesDTO> roles = rolesService.listar();
        
        if(roles.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(roles);
    }

    @PostMapping()
    public ResponseEntity<?> addNewRol(@Valid @RequestBody RolRequest rolRequest){
        try {
            Roles nuevoRol = rolesService.addNewRol(rolRequest);
            return ResponseEntity.ok(nuevoRol);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRol(@PathVariable Long id){
        return rolesService.deleteRol(id);
    }

    @PutMapping("/update/{id}")
    public String updateRol(@PathVariable Long id, @Valid @RequestBody RolRequest rolRequest){
        return rolesService.updateRol(id, rolRequest);
    }

}
