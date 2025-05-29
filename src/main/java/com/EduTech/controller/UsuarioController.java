/** Autor Juan Olguin
 *
 */

package com.EduTech.controller;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
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
    public ResponseEntity<?> addNewUser(@RequestBody UsuarioDTO usuarioDTO){
        usuarioService.addNewUsuario(usuarioDTO);
        return ResponseEntity.ok("Usuario creado");
    }

    // es el cual me permitira ingresar el id del profesor y me listara los cursos asignados previamente
    @GetMapping("/profesor/detalle/{id}")
    public ResponseEntity<?> obtenerDetalleProfesor(@PathVariable Long id) {
        try {
            ProfesorDetalleCursoDTO detalle = usuarioService.obtenerDetalleProfesorConCursos(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // me permitira actualizar (Conjunto de ids)
    @PutMapping("/cursos/{idProfesor}")
    public ResponseEntity<String> reemplazarCursos(@PathVariable Long idProfesor, @RequestBody List<Long> idsCursos) {
        usuarioService.reemplazarCursosDeProfesor(idProfesor, idsCursos);
        return ResponseEntity.ok("Cursos actualizados");
    }


    @PatchMapping("/cursos/{idProfesor}")
    public ResponseEntity<String> modificarCursos(@PathVariable Long idProfesor,
                                                  @RequestBody CursoPatchDTO dto) {
        usuarioService.modificarCursosDeProfesor(idProfesor, dto);
        return ResponseEntity.ok("Cursos modificados correctamente");
    }


}
