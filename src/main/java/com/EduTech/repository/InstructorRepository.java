// Victor garces
package com.EduTech.repository;


import com.EduTech.dto.instructorDTO.InstructorDTO;
import com.EduTech.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InstructorRepository  extends JpaRepository<Instructor, Long> {
    @Query("Select distinct new com.EduTech.dto.instructorDTO.InstructorDTO(i)" +
            "from Instructor i")
    List<InstructorDTO> buscarTodosLosInstructores();
}
