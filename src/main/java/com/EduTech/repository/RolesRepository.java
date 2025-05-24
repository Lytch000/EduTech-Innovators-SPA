package com.EduTech.repository;

import com.EduTech.dto.RolesDTO;
import com.EduTech.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("Select distinct new com.EduTech.dto.RolesDTO (r)" +
    "from Roles r")
    List<RolesDTO> buscarTodos();

}
