package com.EduTech.repository;

import com.EduTech.dto.RolesDTO;
import com.EduTech.dto.UsuarioDTO;
import com.EduTech.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("SELECT DISTINCT new com.EduTech.dto.RolesDTO(r) " +
            "FROM Roles r " +
            "LEFT JOIN Usuario u ON u.roles.id = r.id")
    List<RolesDTO> buscarTodos();
}

