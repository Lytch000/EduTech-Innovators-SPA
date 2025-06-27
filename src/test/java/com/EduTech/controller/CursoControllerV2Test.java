package com.EduTech.controller;

import com.EduTech.assemblers.CursoAssembler;
import com.EduTech.service.CursoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CursoControllerV2.class)
public class CursoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private CursoAssembler assembler;

    @Test
    void asignarEstudiante() throws Exception {
        when(cursoService.inscribirEstudianteACurso(1L, 2L)).thenReturn("Estudiante inscrito correctamente al curso.");

        mockMvc.perform(put("/api/v2/cursos/asignar-estudiante/1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Estudiante inscrito correctamente al curso."));
    }

    @Test
    void asignarEstudianteCursoNoEncontrado() throws Exception {
        when(cursoService.inscribirEstudianteACurso(1L, 2L)).thenThrow(new RuntimeException("Curso no encontrado"));

        mockMvc.perform(put("/api/v2/cursos/asignar-estudiante/1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Curso no encontrado"));
    }

    @Test
    void asignarEstudianteUsuarioNoEncontrado() throws Exception {
        when(cursoService.inscribirEstudianteACurso(1L, 2L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(put("/api/v2/cursos/asignar-estudiante/1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void asignarEstudianteIdCursoNulo() throws Exception {
        when(cursoService.inscribirEstudianteACurso(null, 2L)).thenThrow(new IllegalArgumentException("El id del curso no puede ser nulo"));

        mockMvc.perform(put("/api/v2/cursos/asignar-estudiante//2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // PathVariable faltante da 404
    }

    @Test
    void asignarEstudianteIdUsuarioNulo() throws Exception {
        when(cursoService.inscribirEstudianteACurso(1L, null)).thenThrow(new IllegalArgumentException("El id del usuario no puede ser nulo"));

        mockMvc.perform(put("/api/v2/cursos/asignar-estudiante/1/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // PathVariable faltante da 404
    }

    @Test
    void removerEstudiante() throws Exception {
        when(cursoService.removerEstudiantedeCurso(1L, 2L)).thenReturn("Estudiante removido correctamente");

        mockMvc.perform(put("/api/v2/cursos/remover-estudiante/1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Estudiante removido correctamente"));
    }

    @Test
    void removerEstudianteCursoNoEncontrado() throws Exception {
        when(cursoService.removerEstudiantedeCurso(1L, 2L)).thenThrow(new RuntimeException("Curso no encontrado"));

        mockMvc.perform(put("/api/v2/cursos/remover-estudiante/1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Curso no encontrado"));
    }

    @Test
    void removerEstudianteUsuarioNoEncontrado() throws Exception {
        when(cursoService.removerEstudiantedeCurso(1L, 2L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(put("/api/v2/cursos/remover-estudiante/1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void removerEstudianteIdCursoNulo() throws Exception {
        when(cursoService.removerEstudiantedeCurso(null, 2L)).thenThrow(new IllegalArgumentException("El id del curso no puede ser nulo"));

        mockMvc.perform(put("/api/v2/cursos/remover-estudiante//2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void removerEstudianteIdUsuarioNulo() throws Exception {
        when(cursoService.removerEstudiantedeCurso(1L, null)).thenThrow(new IllegalArgumentException("El id del usuario no puede ser nulo"));

        mockMvc.perform(put("/api/v2/cursos/remover-estudiante/1/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
