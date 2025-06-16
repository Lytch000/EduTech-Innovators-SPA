package com.EduTech.service;

import com.EduTech.dto.roles.RolRequest;
import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.repository.RolesRepository;
import com.EduTech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<RolesDTO> listar() {
        List<RolesDTO> rolesDTO = rolesRepository.buscarTodos();
        rolesDTO.forEach(roles -> {
            roles.setUsuarioList(usuarioRepository.findAllByIdRoles(roles.getId()));
        });
        return rolesDTO;
    }

    private void validarDuplicidadNombre(String nombre, Long idActual) {
        Roles existente = rolesRepository.findByNombre(nombre);
        if (existente != null && (idActual == null || !existente.getId().equals(idActual))) {
            throw new IllegalArgumentException("El nombre del rol ya está en uso");
        }
    }

    public Roles addNewRol(RolRequest rolRequest) {
        validarDuplicidadNombre(rolRequest.getNombre(), null);

        Roles rol = new Roles();
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());
        rol.setFechaCreacion(rolRequest.getFechaCreacion());

        return rolesRepository.save(rol);
    }

    public String deleteRol(Long id) {
        if (!rolesRepository.existsById(id)) {
            return "No se encuentra rol especificado";
        } else {
            rolesRepository.deleteById(id);
            return "Eliminado correctamente";
        }
    }

    public String updateRol(Long id, RolRequest rolRequest) {
        if (!rolesRepository.existsById(id)) {
            return "No se encuentra rol indicado";
        }

        validarDuplicidadNombre(rolRequest.getNombre(), id);

        Roles nuevoRol = new Roles();
        nuevoRol.setId(id);
        nuevoRol.setNombre(rolRequest.getNombre());
        nuevoRol.setDescripcion(rolRequest.getDescripcion());
        nuevoRol.setFechaCreacion(rolRequest.getFechaCreacion());
        rolesRepository.save(nuevoRol);
        return "Rol actualizado correctamente";
    }
}
