package com.EduTech.dto.cursoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO que representa los detalles de un profesor junto con los cursos asignados.
 * Incluye información personal del profesor y una lista de cursos.
 * Autor: Franco Carrasco
 * @version 1.0
 */
@Data
@Schema(description = "DTO que representa los detalles de un profesor junto con sus cursos asignados")
public class ProfesorDetalleCursoDTO {

    @Schema(description = "Identificador único del profesor", example = "42")
    private Long id;

    @Schema(description = "Nombre completo del profesor", example = "María González")
    private String nombre;

    @Schema(description = "Correo electrónico del profesor", example = "maria.gonzalez@edutech.com")
    private String email;

    @Schema(description = "RUT del profesor", example = "12.345.678-9")
    private String rut;

    @Schema(description = "Lista de cursos asignados al profesor")
    private List<CursoDTO> cursos;

    /**
     * Constructor para inicializar los datos del profesor y sus cursos.
     * @param id ID del profesor
     * @param nombre Nombre del profesor
     * @param email Correo electrónico
     * @param rut RUT del profesor
     * @param cursos Lista de cursos asignados
     */
    public ProfesorDetalleCursoDTO(Long id, String nombre, String email, String rut, List<CursoDTO> cursos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rut = rut;
        this.cursos = cursos;
    }
}
