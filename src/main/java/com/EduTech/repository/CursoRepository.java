package com.EduTech.repository;


import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("Select distinct new com.EduTech.dto.cursoDTO.CursoDTO(c)" +
            "from Curso c ")
    List<CursoDTO> buscarTodosLosCursos();

}
