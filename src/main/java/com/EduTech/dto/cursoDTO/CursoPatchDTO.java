package com.EduTech.dto.cursoDTO;

import lombok.Data;

import java.util.List;

@Data
public class CursoPatchDTO {
    private List<Long> idsAgregar;
    private List<Long> idsEliminar;
}
