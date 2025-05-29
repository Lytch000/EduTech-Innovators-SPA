package com.EduTech.service;

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

    public List<RolesDTO> listar(){

        List<RolesDTO> rolesDTO = rolesRepository.buscarTodos();
        rolesDTO.forEach( roles -> {
            roles.setUsuarioList(usuarioRepository.findAllByIdRoles(roles.getId()));
        });
        return rolesDTO;
    }

    public Roles addNewRol(Roles rol){
        return rolesRepository.save(rol);
    }

    public String deleteRol(Long id){
        if(!rolesRepository.existsById(id)){
            return "No se encuentra rol especificado";
        }else {
            rolesRepository.deleteById(id);
            return "Eliminado correctamente";
        }
    }

    public String updateRol(Long id, Roles nuevoRol){
        if (!rolesRepository.existsById(id)) {
            return "No se encuentra rol indicado";
        }
        nuevoRol.setId(id);
        rolesRepository.save(nuevoRol);
        return "Rol actualizado correctamente";
    }




}
