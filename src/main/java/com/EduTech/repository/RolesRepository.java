/*
Autor Juan Olguin
 */

package com.EduTech.repository;

import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("SELECT DISTINCT new com.EduTech.dto.roles.RolesDTO(r) " +
            "FROM Roles r " +
            "LEFT JOIN Usuario u ON u.roles.id = r.id")
    List<RolesDTO> buscarTodos();

    Roles findByNombre(String nombre);
}

