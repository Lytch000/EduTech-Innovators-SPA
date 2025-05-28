// Victor garces
package com.EduTech.controller;

import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping()
    public ResponseEntity<List<CursoDTO>> listar(){
        List<CursoDTO> cursos = cursoService.listar();
        if (cursos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cursos);
        }

        return ResponseEntity.ok(cursos);
    }

    // Ingresamos curso nuevo
    @PostMapping()
    public Curso addNewCurso(@RequestBody Curso curso){
        return cursoService.addNewCurso(curso);
    }


    //Actualizamos curso
    @PutMapping()
    public ResponseEntity<String> actualizarCurso(@RequestBody CursoDTO cursoDTO) {
        if (cursoDTO.getIdCurso() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: ID del curso es obligatorio.");
        }
        try {
            String mensaje = cursoService.actualizarCurso(cursoDTO);
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el curso: " + e.getMessage());
        }
    }

    // Eliminados curso por el ID registrado en base de datos
    @DeleteMapping("/delete/{idCurso}")
    public String deleteCurso(@PathVariable Long idCurso){
        return  cursoService.deleteCurso(idCurso);
    }

    @PutMapping("/remover-profesor/{idCurso}")
    public ResponseEntity<String> removerProfesor(@PathVariable Long idCurso) {
        try {
            String mensaje = cursoService.removerProfesorDeCurso(idCurso);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al remover el profesor: " + e.getMessage());
        }
    }

    @PutMapping("/asignar-profesor/{idCurso}")
    public ResponseEntity<String> asignarProfesor(@PathVariable Long idCurso) {
        try {
            String mensaje = cursoService.asignarProfesorDeCurso(idCurso);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al asignar el profesor: " + e.getMessage());
        }
    }

}








