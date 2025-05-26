//Victor garces
package com.EduTech.service;

import com.EduTech.dto.instructorDTO.InstructorDTO;
import com.EduTech.model.Instructor;
import com.EduTech.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public List<InstructorDTO> listar(){
        List<InstructorDTO> instructorDto = instructorRepository.buscarTodosLosInstructores();
        return instructorDto;
    }

    public Instructor addNewInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }

}
