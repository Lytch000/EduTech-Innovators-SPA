// Victor garces
package com.EduTech.controller;
 
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.model.Curso;
import com.EduTech.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Cursos", description = "Operaciones relacionadas con la gestión de cursos")
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




    // Eliminados curso por el ID registrado en base de datos
    @DeleteMapping("/delete/{idCurso}")
    public String deleteCurso(@PathVariable Long idCurso){
        return  cursoService.deleteCurso(idCurso);
    }

    //Eliminamos profesor del curso ----
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

    //Asignamos profesor ---
    @PutMapping("/asignar-profesor/{idCurso}/{idUsuario}")
    public ResponseEntity<String> asignarProfesor(@PathVariable Long idCurso, @PathVariable Long idUsuario) {
        try {
            String mensaje = cursoService.asignarProfesorACurso(idCurso, idUsuario);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al asignar el profesor: " + e.getMessage());
        }
    }

    //Actualizamos curso --
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

    //Autor Juan Olguin
    @Operation(
        summary = "Inscribir estudiante en un curso",
        description = "Permite inscribir un estudiante existente en un curso existente usando sus IDs."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estudiante inscrito correctamente al curso."),
        @ApiResponse(responseCode = "400", description = "Error de validación o datos incorrectos.")
    })
    @PutMapping("/inscribirEstudiante/{idCurso}/{idUsuario}")
    public ResponseEntity<String> inscribirEstudiante(
        @Parameter(description = "ID del curso", required = true) @PathVariable Long idCurso,
        @Parameter(description = "ID del usuario (estudiante)", required = true) @PathVariable Long idUsuario) {
        try {
            String mensaje = cursoService.inscribirEstudianteACurso(idCurso, idUsuario);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Autor Juan Olguin
    @Operation(
        summary = "Remover estudiante de un curso",
        description = "Permite remover a un estudiante de un curso usando sus IDs."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estudiante removido correctamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación o datos incorrectos.")
    })
    @PutMapping("/removerEstudiante/{idCurso}/{idUsuario}")
    public ResponseEntity<String> removerEstudiante(
        @Parameter(description = "ID del curso", required = true) @PathVariable Long idCurso,
        @Parameter(description = "ID del usuario (estudiante)", required = true) @PathVariable Long idUsuario) {
        try {
            String mensaje = cursoService.removerEstudiantedeCurso(idCurso, idUsuario);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Autor Victor Garces
    // me permitira actualizar (Conjunto de ids)
    @PutMapping("/cursos/{idProfesor}")
    public ResponseEntity<String> reemplazarCursos(@PathVariable Long idProfesor, @RequestBody List<Long> idsCursos) {
        cursoService.reemplazarCursosDeProfesor(idProfesor, idsCursos);
        return ResponseEntity.ok("Cursos actualizados");
    }

    //Autor Victor Garces
    @PatchMapping("/cursos/{idProfesor}")
    public ResponseEntity<String> modificarCursos(@PathVariable Long idProfesor,
                                                  @RequestBody CursoPatchDTO dto) {
        cursoService.modificarCursosDeProfesor(idProfesor, dto);
        return ResponseEntity.ok("Cursos modificados correctamente");
    }

}








