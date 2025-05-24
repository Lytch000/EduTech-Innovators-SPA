package com.EduTech.controller;

import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cursos/")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/listar")
    public ResponseEntity<List<CursoDTO>> listar(){
        List<CursoDTO> cursos = cursoService.listar();
        if (cursos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cursos);
        }

        return ResponseEntity.ok(cursos);
    }

    @PostMapping("/creacion")
    public Curso addNewCurso(@RequestBody Curso curso){
        return cursoService.addNewCurso(curso);
    }

}
