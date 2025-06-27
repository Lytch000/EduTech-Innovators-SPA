package com.EduTech.controller;

import com.EduTech.assemblers.CursoAssembler;
import com.EduTech.assemblers.CursoModelAssembler;
import com.EduTech.dto.MensajeDTO;
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.model.Curso;
import com.EduTech.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v2/cursos")
public class CursoControllerV2 {

    private final CursoService cursoService;
    private final CursoModelAssembler cursoModelAssembler;
    private final CursoAssembler assembler; // De feature/pruebas-acoplamiento

    public CursoControllerV2(CursoService cursoService, CursoModelAssembler cursoModelAssembler, CursoAssembler assembler) {
        this.cursoService = cursoService;
        this.cursoModelAssembler = cursoModelAssembler;
        this.assembler = assembler;
    }

    // Métodos de feature/pruebas-acoplamiento ---------------------

    @PutMapping("/asignar-estudiante/{idCurso}/{idEstudiante}")
    @Operation(
        summary = "Asignar estudiante a curso",
        description = "Asigna un estudiante a un curso y devuelve el curso actualizado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estudiante asignado correctamente"),
            @ApiResponse(responseCode = "404", description = "Curso o estudiante no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
        }
    )
    public ResponseEntity<?> asignarEstudiante(@PathVariable Long idCurso, @PathVariable Long idEstudiante) {
        String mensaje = cursoService.inscribirEstudianteACurso(idCurso, idEstudiante);
        MensajeDTO mensajeDTO = new MensajeDTO(mensaje);

        EntityModel<MensajeDTO> model = EntityModel.of(
            mensajeDTO,
            linkTo(methodOn(CursoControllerV2.class).asignarEstudiante(idCurso, idEstudiante)).withSelfRel(),
            linkTo(methodOn(CursoControllerV2.class).removerEstudiante(idCurso, idEstudiante)).withRel("remover-estudiante")
        );
        return ResponseEntity.ok(model);
    }

    @PutMapping("/remover-estudiante/{idCurso}/{idEstudiante}")
    @Operation(
        summary = "Remover estudiante de curso",
        description = "Remueve un estudiante de un curso y devuelve el curso actualizado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estudiante removido correctamente"),
            @ApiResponse(responseCode = "404", description = "Curso o estudiante no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud")
        }
    )
    public ResponseEntity<?> removerEstudiante(@PathVariable Long idCurso, @PathVariable Long idEstudiante) {
        String mensaje = cursoService.removerEstudiantedeCurso(idCurso, idEstudiante);
        MensajeDTO mensajeDTO = new MensajeDTO(mensaje);

        EntityModel<MensajeDTO> model = EntityModel.of(
            mensajeDTO,
            linkTo(methodOn(CursoControllerV2.class).removerEstudiante(idCurso, idEstudiante)).withSelfRel(),
            linkTo(methodOn(CursoControllerV2.class).asignarEstudiante(idCurso, idEstudiante)).withRel("asignar-estudiante")
        );
        return ResponseEntity.ok(model);
    }

    // Métodos de feature/victor-v6 ---------------------

    @GetMapping()
    @Operation(summary = "Obtener los cursos", description = "Obtiene una lista de todos los cursos")
    public ResponseEntity<CollectionModel<EntityModel<CursoDTO>>> listar() {
        List<CursoDTO> cursos = cursoService.listar();
        if (cursos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<EntityModel<CursoDTO>> cursosHateoas = cursos.stream()
                .map(cursoModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CursoDTO>> collectionModel = CollectionModel.of(
                cursosHateoas,
                linkTo(methodOn(CursoControllerV2.class).listar()).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping()
    @Operation(summary = "Agregar un nuevo curso", description = "Crea un nuevo curso y lo guarda en la base de datos")
    public ResponseEntity<EntityModel<CursoDTO>> addNewCurso(@RequestBody Curso curso) {
        Curso cursoCreado = cursoService.addNewCurso(curso);
        CursoDTO cursoDTO = new CursoDTO(cursoCreado);
        EntityModel<CursoDTO> recurso = cursoModelAssembler.toModel(cursoDTO);
        return ResponseEntity.ok(recurso);
    }

    @DeleteMapping("/delete/{idCurso}")
    @Operation(summary = "Eliminar un curso", description = "Elimina un curso por su ID")
    public ResponseEntity<EntityModel<Map<String, String>>> deleteCurso(@PathVariable Long idCurso) {
        String resultado = cursoService.deleteCurso(idCurso);
        Map<String, String> body = Map.of("mensaje", resultado);

        EntityModel<Map<String, String>> recurso = EntityModel.of(
            body,
            linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos")
        );

        return "Curso eliminado exitosamente.".equals(resultado)
            ? ResponseEntity.ok(recurso)
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body(recurso);
    }

    @PutMapping("/remover-profesor/{idCurso}")
    @Operation(summary = "Remover profesor de un curso", description = "Elimina la asignación de profesor de un curso por su ID.")
    public ResponseEntity<EntityModel<Map<String, String>>> removerProfesor(@PathVariable Long idCurso) {
        try {
            String mensaje = cursoService.removerProfesorDeCurso(idCurso);
            Map<String, String> body = Map.of("message", mensaje);
            EntityModel<Map<String, String>> recurso = EntityModel.of(body, linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
            return ResponseEntity.ok(recurso);
        } catch (Exception e) {
            Map<String, String> body = Map.of("message", "Error al remover el profesor: " + e.getMessage());
            EntityModel<Map<String, String>> recurso = EntityModel.of(body, linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(recurso);
        }
    }

    @PutMapping("/asignar-profesor/{idCurso}/{idUsuario}")
    @Operation(summary = "Asignar profesor a un curso", description = "Asigna un profesor (usuario) a un curso específico.")
    public ResponseEntity<EntityModel<Map<String, String>>> asignarProfesor(@PathVariable Long idCurso, @PathVariable Long idUsuario) {
        try {
            String mensaje = cursoService.asignarProfesorACurso(idCurso, idUsuario);
            Map<String, String> body = Map.of("message", mensaje);
            EntityModel<Map<String, String>> recurso = EntityModel.of(body, linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
            return ResponseEntity.ok(recurso);
        } catch (Exception e) {
            Map<String, String> body = Map.of("message", "Error al asignar el profesor: " + e.getMessage());
            EntityModel<Map<String, String>> recurso = EntityModel.of(body, linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(recurso);
        }
    }

    @PutMapping()
    @Operation(summary = "Actualizar un curso", description = "Actualiza los datos de un curso existente usando su ID.")
    public ResponseEntity<EntityModel<Map<String, String>>> actualizarCurso(@RequestBody CursoDTO cursoDTO) {
        if (cursoDTO.getIdCurso() == null) {
            Map<String, String> body = Map.of("message", "Error: ID del curso es obligatorio.");
            EntityModel<Map<String, String>> recurso = EntityModel.of(body, linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(recurso);
        }
        try {
            String mensaje = cursoService.actualizarCurso(cursoDTO);
            Map<String, String> body = Map.of("message", mensaje);
            EntityModel<Map<String, String>> recurso = EntityModel.of(body, linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
            return ResponseEntity.ok(recurso);
        } catch (Exception e) {
            Map<String, String> body = Map.of("message", "Error al actualizar el curso: " + e.getMessage());
            EntityModel<Map<String, String>> recurso = EntityModel.of(body, linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(recurso);
        }
    }

    @PutMapping("/cursos/{idProfesor}")
    @Operation(summary = "Reemplazar cursos de un profesor", description = "Reemplaza todos los cursos asignados a un profesor por un nuevo conjunto de cursos.")
    public ResponseEntity<String> reemplazarCursos(@PathVariable Long idProfesor, @RequestBody List<Long> idsCursos) {
        cursoService.reemplazarCursosDeProfesor(idProfesor, idsCursos);
        return ResponseEntity.ok("Cursos actualizados");
    }

    @PatchMapping("/cursos/{idProfesor}")
    @Operation(summary = "Modificar cursos de un profesor", description = "Agrega o elimina cursos asignados a un profesor según los IDs enviados.")
    public ResponseEntity<String> modificarCursos(@PathVariable Long idProfesor, @RequestBody CursoPatchDTO dto) {
        cursoService.modificarCursosDeProfesor(idProfesor, dto);
        return ResponseEntity.ok("Cursos modificados correctamente");
    }
}
