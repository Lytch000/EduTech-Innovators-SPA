package com.EduTech.repository;

import com.EduTech.model.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RolesRepositoryTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Test
    void testBuscarTodos() {
        Roles rol1 = new Roles();
        rol1.setId(1L);
        rol1.setNombre("ADMIN");
        rol1.setDescripcion("Administrador");
        rol1.setFechaCreacion(new Date());

        Roles rol2 = new Roles();
        rol2.setId(2L);
        rol2.setNombre("USER");
        rol2.setDescripcion("Usuario");
        rol2.setFechaCreacion(new Date());

        rolesRepository.save(rol1);
        rolesRepository.save(rol2);

        List<?> rolesDTOs = rolesRepository.buscarTodos();
        assertFalse(rolesDTOs.isEmpty());
        // Puedes agregar más asserts según tu DTO
    }
}
