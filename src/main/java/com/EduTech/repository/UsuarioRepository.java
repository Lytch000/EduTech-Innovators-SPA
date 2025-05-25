/** Autor Juan Olguin
 *
 */

package com.EduTech.repository;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("Select distinct new com.EduTech.dto.UsuarioDTO(c)" +
    "from Usuario c ")
    List<UsuarioDTO> buscarTodos();

    @Query("select new com.EduTech.dto.UsuarioDTO(u)" +
            " from Usuario u " +
            " where u.roles.id = :idRoles ")
    List<UsuarioDTO> findByIdUsuario(@Param("idRoles") Long idRoles);

}
