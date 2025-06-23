// Victor garces
package com.EduTech.controller;

import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.model.Curso;
import com.EduTech.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cursos")
@Tag(name = "Cursos" , description = "Operaciones relacionados con cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping()
    @Operation(
    summary = "Obtener los cursos", description = "Obtiene una lista de todos los cursos",
    responses = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "204", description = "No hay cursos disponibles"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
    )

    public ResponseEntity<List<CursoDTO>> listar(){
        List<CursoDTO> cursos = cursoService.listar();
        if (cursos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cursos);
        }

        return ResponseEntity.ok(cursos);
    }

   
    // Ingresamos curso nuevo
    @PostMapping()
    @Operation(
    summary = "Agregar un nuevo curso",
    description = "Crea un nuevo curso y lo guarda en la base de datos",
    responses = {
        @ApiResponse(responseCode = "200", description = "Curso creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    }
    )
    public Curso addNewCurso(@RequestBody Curso curso){
        return cursoService.addNewCurso(curso);
    }



    
    // Eliminados curso por el ID registrado en base de datos
    @DeleteMapping("/delete/{idCurso}")
    @Operation(
    summary = "Eliminar un curso",
    description = "Elimina un curso por su ID",
    responses = {
        @ApiResponse(responseCode = "200", description = "Curso eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    }
    )
    public String deleteCurso(@PathVariable Long idCurso){
        return  cursoService.deleteCurso(idCurso);
    }


    //Eliminamos profesor del curso ----
    @PutMapping("/remover-profesor/{idCurso}")
    @Operation(
    summary = "Remover profesor de un curso",
    description = "Elimina la asignación de profesor de un curso por su ID.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Profesor removido correctamente"),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado"),
        @ApiResponse(responseCode = "400", description = "El curso no tiene profesor asignado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
    )
    public ResponseEntity<String> removerProfesor(
    @Parameter(description = "ID del curso del que se removerá el profesor", required = true, example = "1")    
    @PathVariable Long idCurso) {
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
    @Operation(
    summary = "Asignar profesor a un curso",
    description = "Asigna un profesor (usuario) a un curso específico.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Profesor asignado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error al asignar el profesor")
    }
    )
    public ResponseEntity<String> asignarProfesor(
        @Parameter(description = "ID del curso al que se asignará el profesor", required = true, example = "1")
        @PathVariable Long idCurso,
        @Parameter(description = "ID del profesor/ usuario que se asignará", required = true, example = "1")
        @PathVariable Long idUsuario) {
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
    @Operation(
    summary = "Actualizar un curso",
    description = "Actualiza los datos de un curso existente usando su ID.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Curso actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "ID del curso es obligatorio"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    }
)
    public ResponseEntity<String> actualizarCurso(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos del curso a actualizar. El campo 'idCurso' es obligatorio.", required = true,
        content = @Content(
        schema = @Schema(implementation = CursoDTO.class),
        examples = @ExampleObject(
            value = "{\n" +
                    "  \"idCurso\": 1,\n" +
                    "  \"nombreCurso\": \"Matemáticas\",\n" +
                    "  \"descripcion\": \"Curso de matemáticas básicas\",\n" +
                    "  \"categoria\": \"Ciencias\",\n" +
                    "  \"horasDuracion\": 40,\n" +
                    "  \"precioCurso\": 100.0,\n" +
                    "  \"fechaPublicacion\": \"2024-06-21T00:00:00.000+00:00\"\n" +
                    "}"
        )
    ))
    @RequestBody CursoDTO cursoDTO) {
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
    @PutMapping("/inscribirEstudiante/{idCurso}/{idUsuario}")
    public ResponseEntity<String> inscribirEstudiante(@PathVariable Long idCurso, @PathVariable Long idUsuario) {
        try {
            String mensaje = cursoService.inscribirEstudianteACurso(idCurso, idUsuario);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Autor Juan Olguin
    @PutMapping("/removerEstudiante/{idCurso}/{idUsuario}")
    public ResponseEntity<String> removerEstudiante(@PathVariable Long idCurso, @PathVariable Long idUsuario){
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
    @Operation(
    summary = "Reemplazar cursos de un profesor",
    description = "Reemplaza todos los cursos asignados a un profesor por un nuevo conjunto de cursos.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Cursos actualizados correctamente"),
        @ApiResponse(responseCode = "404", description = "Profesor o curso no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    }
    )
    public ResponseEntity<String> reemplazarCursos(
    @Parameter(description = "ID del profesor", required = true, example = "1")    
    @PathVariable Long idProfesor,
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Lista de IDs de cursos a asignar al profesor.",
        required = true,
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(type = "integer", example = "10")),
            examples = @ExampleObject(value = "[10, 20, 30]")
        )
    )
     @RequestBody List<Long> idsCursos) {
        cursoService.reemplazarCursosDeProfesor(idProfesor, idsCursos);
        return ResponseEntity.ok("Cursos actualizados");
    }



    //Autor Victor Garces
    @PatchMapping("/cursos/{idProfesor}")
    @Operation(
    summary = "Modificar cursos de un profesor",
    description = "Agrega o elimina cursos asignados a un profesor según los IDs enviados.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Cursos modificados correctamente"),
        @ApiResponse(responseCode = "404", description = "Profesor o curso no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    }
    )
    public ResponseEntity<String> modificarCursos(
        @Parameter(description = "ID del profesor", required = true, example = "1")
        @PathVariable Long idProfesor,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Objeto con listas de IDs de cursos a agregar o eliminar.",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CursoPatchDTO.class),
            examples = @ExampleObject(
                value = "{\n  \"idsAgregar\": [10, 20],\n  \"idsEliminar\": [30]\n}"
            )
        )
    )
        @RequestBody CursoPatchDTO dto) {
        cursoService.modificarCursosDeProfesor(idProfesor, dto);
        return ResponseEntity.ok("Cursos modificados correctamente");
    }

}








