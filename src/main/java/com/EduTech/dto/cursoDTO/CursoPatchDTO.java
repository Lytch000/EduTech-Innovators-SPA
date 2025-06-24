package com.EduTech.dto.cursoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO utilizado para realizar actualizaciones parciales en las relaciones de un curso.
 * Permite agregar o eliminar entidades relacionadas mediante sus identificadores.
 * Autor: Franco Carrasco
 * @version 1.0
 */
@Data
@Schema(description = "DTO para actualizar parcialmente las relaciones de un curso, como usuarios, módulos, etc.")
public class CursoPatchDTO {

    @Schema(description = "Lista de IDs que se desean agregar al curso", example = "[101, 102, 103]")
    private List<Long> idsAgregar;

    @Schema(description = "Lista de IDs que se desean eliminar del curso", example = "[201, 202]")
    private List<Long> idsEliminar;
}
