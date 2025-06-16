package com.EduTech.controller;

import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.repository.RolesRepository;
import com.EduTech.service.RolesService;
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
    public ResponseEntity<?> addNewRol(@RequestBody Roles rol){
        try {
            Roles nuevoRol = rolesService.addNewRol(rol);
            return ResponseEntity.ok(nuevoRol);
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (msg.contains("vacío")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            } else if (msg.contains("ya existe")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRol(@PathVariable Long id){
        return rolesService.deleteRol(id);
    }

    @PutMapping("/update/{id}")
    public String updateRol(@PathVariable Long id, @RequestBody Roles rol){
        return rolesService.updateRol(id, rol);
    }

}
