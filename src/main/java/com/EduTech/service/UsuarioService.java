/** Autor Juan Olguin
 *
 */

package com.EduTech.service;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.EduTech.repository.RolesRepository;
import com.EduTech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public List<UsuarioDTO> listar(){

        List<UsuarioDTO> usuarioDTO = usuarioRepository.buscarTodos();
        return usuarioDTO;
    }

    public String addNewUsuario(UsuarioDTO dto){

        Roles rol = rolesRepository.findById(dto.getId_rol_fk())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setNombre(dto.getNombre());
        usuario.setEdad(dto.getEdad());
        usuario.setEmail(dto.getEmail());
        usuario.setCelular(dto.getCelular());
        usuario.setRoles(rol);

        usuarioRepository.save(usuario);

        return "Usuario registrado correctamente";

    }

    public UsuarioDTO loginUsuario(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password)
                .map(UsuarioDTO::new)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }

}
