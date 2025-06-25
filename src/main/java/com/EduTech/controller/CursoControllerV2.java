package com.EduTech.controller;

import com.EduTech.assemblers.CursoAssembler;
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/cursos")
public class CursoControllerV2 {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoAssembler assembler;

    /**
     * Asigna un estudiante a un curso.
     */
    @Operation(
        summary = "Asignar estudiante a curso",
        description = "Asigna un estudiante a un curso y devuelve el curso actualizado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estudiante asignado correctamente"),
            @ApiResponse(responseCode = "404", description = "Curso o estudiante no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
        }
    )
    @PutMapping("/asignar-estudiante/{idCurso}/{idEstudiante}")
    public ResponseEntity<String> asignarEstudiante(@PathVariable Long idCurso, @PathVariable Long idEstudiante) {
        try {
            String mensaje = cursoService.inscribirEstudianteACurso(idCurso, idEstudiante);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Remueve un estudiante de un curso.
     */
    @Operation(
        summary = "Remover estudiante de curso",
        description = "Remueve un estudiante de un curso y devuelve el curso actualizado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estudiante removido correctamente"),
            @ApiResponse(responseCode = "404", description = "Curso o estudiante no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
        }
    )
    @PutMapping("/remover-estudiante/{idCurso}/{idEstudiante}")
    public ResponseEntity<String> removerEstudiante(@PathVariable Long idCurso, @PathVariable Long idEstudiante) {
        try {
            String mensaje = cursoService.removerEstudiantedeCurso(idCurso, idEstudiante);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
