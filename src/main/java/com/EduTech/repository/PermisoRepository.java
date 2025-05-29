package com.EduTech.repository;

import com.EduTech.model.Permiso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Franco Carrasco
 */
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}

