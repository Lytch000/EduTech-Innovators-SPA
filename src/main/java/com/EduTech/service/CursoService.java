package com.EduTech.service;


import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;


    public List<CursoDTO> listar(){
        List<CursoDTO> cursoDto = cursoRepository.buscarTodosLosCursos();
        return cursoDto;
    }

    public Curso addNewCurso(Curso curso){
        return cursoRepository.save(curso);
    }

}
