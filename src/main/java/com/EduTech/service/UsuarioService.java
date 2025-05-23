/** Autor Juan Olguin
 *
 */

package com.EduTech.service;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.model.Usuario;
import com.EduTech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listar(){

        List<UsuarioDTO> usuarioDTO = usuarioRepository.buscarTodos();
        return usuarioDTO;
    }

    public Usuario addNewUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

}
