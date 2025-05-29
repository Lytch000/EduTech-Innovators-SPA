package com.EduTech.controller;

import com.EduTech.dto.RolesDTO;
import com.EduTech.model.Roles;
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

    @GetMapping()
    public ResponseEntity<List<RolesDTO>> listar(){
        List<RolesDTO> roles = rolesService.listar();
        if(roles.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roles);
        }

        return ResponseEntity.ok(roles);
    }

    @PostMapping()
    public Roles addNewRol(@RequestBody Roles rol){
        return rolesService.addNewRol(rol);
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
