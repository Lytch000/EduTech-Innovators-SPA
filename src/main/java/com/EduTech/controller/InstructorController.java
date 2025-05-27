// Victor garces
package com.EduTech.controller;

import com.EduTech.dto.instructorDTO.InstructorAsignacionDTO;
import com.EduTech.dto.instructorDTO.InstructorDTO;
import com.EduTech.model.Instructor;
import com.EduTech.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    //Lista mis instructores
    @GetMapping()
    public ResponseEntity<List<InstructorDTO>> listar(){
        List<InstructorDTO> instructor = instructorService.listar();
        if (instructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(instructor);

        }
        return ResponseEntity.ok(instructor);
    }

    // Crea el instructor
    @PostMapping()
    public Instructor addNewCurso(@RequestBody Instructor instructor){
        return instructorService.addNewInstructor(instructor);
    }

    // Eliminamos instructor
    @DeleteMapping("/delete/{idInstructor}")
    public String deleteCurso(@PathVariable Long idInstructor){
        return instructorService.deleteCurso(idInstructor);
    }



    // Asignacion de instructor a un curso
    @PostMapping("/asignar")
    public ResponseEntity<String> asignarInstructor(@RequestBody InstructorAsignacionDTO dto) {
        String resultado = instructorService.asignarInstructor(dto);
        return ResponseEntity.ok(resultado);
    }



}
