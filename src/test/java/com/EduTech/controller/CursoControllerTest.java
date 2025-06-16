package com.EduTech.controller;
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.model.Curso;
import com.EduTech.service.CursoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;;


@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Test
    void testListarCursos_Vacio() throws Exception {
    when(cursoService.listar()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/v1/cursos")) 
            .andExpect(status().isNoContent())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testListarCursos_NoVacio() throws Exception {
    CursoDTO curso1 = new CursoDTO();
    curso1.setIdCurso(1L);
    curso1.setNombreCurso("Java Básico");

    CursoDTO curso2 = new CursoDTO();
    curso2.setIdCurso(2L);
    curso2.setNombreCurso("Spring Boot");

    List<CursoDTO> cursos = List.of(curso1, curso2);

    when(cursoService.listar()).thenReturn(cursos);

    mockMvc.perform(get("/api/v1/cursos"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].idCurso").value(1))
            .andExpect(jsonPath("$[0].nombreCurso").value("Java Básico"))
            .andExpect(jsonPath("$[1].idCurso").value(2))
            .andExpect(jsonPath("$[1].nombreCurso").value("Spring Boot"));
    }



    @Test
    void testAddNewCurso() throws Exception {
    Curso curso = new Curso();
    curso.setIdCurso(1L);
    curso.setNombreCurso("Spring Boot");

    // Simula que el servicio retorna el curso guardado
    when(cursoService.addNewCurso(any(Curso.class))).thenReturn(curso);

    String cursoJson = """
        {
            "idCurso": 1,
            "nombreCurso": "Spring Boot"
        }
        """;

    mockMvc.perform(post("/api/v1/cursos") 
            .contentType(MediaType.APPLICATION_JSON)
            .content(cursoJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idCurso").value(1))
            .andExpect(jsonPath("$.nombreCurso").value("Spring Boot"));
    }

    @Test
    void testDeleteCurso() throws Exception {
    Long idCurso = 1L;
    when(cursoService.deleteCurso(idCurso)).thenReturn("Curso eliminado exitosamente.");

    mockMvc.perform(delete("/api/v1/cursos/delete/{idCurso}", idCurso))
            .andExpect(status().isOk())
            .andExpect(content().string("Curso eliminado exitosamente."));
    }



    @Test
    void testRemoverProfesorDeCurso_Exito() throws Exception {
    Long idCurso = 1L;
    when(cursoService.removerProfesorDeCurso(idCurso)).thenReturn("Profesor removido correctamente.");

    mockMvc.perform(put("/api/v1/cursos/remover-profesor/{idCurso}", idCurso))
            .andExpect(status().isOk())
            .andExpect(content().string("Profesor removido correctamente."));
    }

    @Test
    void testRemoverProfesorDeCurso_Error() throws Exception {
    Long idCurso = 1L;
    when(cursoService.removerProfesorDeCurso(idCurso)).thenThrow(new RuntimeException("No se pudo remover"));

    mockMvc.perform(put("/api/v1/cursos/remover-profesor/{idCurso}", idCurso))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Error al remover el profesor: No se pudo remover"));
    }

    

    @Test
    void testasignarProfesor() throws Exception {
    Long idCurso = 1L;
    Long idUsuario = 2L;
    when(cursoService.asignarProfesorACurso(idCurso, idUsuario)).thenReturn("Profesor asignado correctamente.");

    mockMvc.perform(put("/api/v1/cursos/asignar-profesor/{idCurso}/{idUsuario}", idCurso, idUsuario))
            .andExpect(status().isOk())
            .andExpect(content().string("Profesor asignado correctamente."));

    }

    @Test
    void testAsignarProfesor_Error() throws Exception {
    Long idCurso = 1L;
    Long idUsuario = 2L;
    when(cursoService.asignarProfesorACurso(idCurso, idUsuario)).thenThrow(new RuntimeException("No se pudo asignar"));

    mockMvc.perform(put("/api/v1/cursos/asignar-profesor/{idCurso}/{idUsuario}", idCurso, idUsuario))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Error al asignar el profesor: No se pudo asignar"));
    }

    @Test
    void testActualizarCurso_IdCursoNull() throws Exception {
    String cursoJson = """
        {
            "nombreCurso": "Curso Actualizado"
        }
        """;

    mockMvc.perform(put("/api/v1/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(cursoJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Error: ID del curso es obligatorio."));
    }

    @Test
    void testActualizarCurso_Exito() throws Exception {
    CursoDTO cursoDTO = new CursoDTO();
    cursoDTO.setIdCurso(1L);
    cursoDTO.setNombreCurso("Curso Actualizado");

    when(cursoService.actualizarCurso(any(CursoDTO.class))).thenReturn("Curso actualizado correctamente.");

    String cursoJson = """
        {
            "idCurso": 1,
            "nombreCurso": "Curso Actualizado"
        }
        """;

    mockMvc.perform(put("/api/v1/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(cursoJson))
            .andExpect(status().isOk())
            .andExpect(content().string("Curso actualizado correctamente."));
    }

    @Test
    void testActualizarCurso_Error() throws Exception {
    when(cursoService.actualizarCurso(any(CursoDTO.class)))
            .thenThrow(new RuntimeException("Fallo inesperado"));

    String cursoJson = """
        {
            "idCurso": 1,
            "nombreCurso": "Curso Actualizado"
        }
        """;

    mockMvc.perform(put("/api/v1/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(cursoJson))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Error al actualizar el curso: Fallo inesperado"));
    }

    @Test
    void testreemplazarCursos() throws Exception {
    Long idProfesor = 1L;
    List<Long> idsCursos = List.of(10L, 20L);

    // No necesito mockear el retorno porque el método del servicio es void
    doNothing().when(cursoService).reemplazarCursosDeProfesor(eq(idProfesor), eq(idsCursos));

    String idsCursosJson = "[10, 20]";

    mockMvc.perform(put("/api/v1/cursos/cursos/{idProfesor}", idProfesor)
            .contentType(MediaType.APPLICATION_JSON)
            .content(idsCursosJson))
            .andExpect(status().isOk())
            .andExpect(content().string("Cursos actualizados"));
    }


    @Test
    void testModificarCursosDeProfesor() throws Exception {
    Long idProfesor = 1L;
    CursoPatchDTO dto = new CursoPatchDTO();
    dto.setIdsAgregar(List.of(10L, 20L));
    dto.setIdsEliminar(List.of(30L));

    // No necesitas mockear el retorno porque el método del servicio es void
    doNothing().when(cursoService).modificarCursosDeProfesor(eq(idProfesor), any(CursoPatchDTO.class));

    String dtoJson = """
        {
            "idsAgregar": [10, 20],
            "idsEliminar": [30]
        }
        """;

    mockMvc.perform(patch("/api/v1/cursos/cursos/{idProfesor}", idProfesor)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dtoJson))
            .andExpect(status().isOk())
            .andExpect(content().string("Cursos modificados correctamente"));
    }

    

}
