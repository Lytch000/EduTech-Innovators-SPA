/** Autor Juan Olguin
 *
 */

package com.EduTech.controller;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.model.Usuario;
import com.EduTech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar(){
        List<UsuarioDTO> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuarios);
        }

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping()
    public Usuario addNewUsuario(@RequestBody Usuario usuario){
        return usuarioService.addNewUsuario(usuario);
    }

}
