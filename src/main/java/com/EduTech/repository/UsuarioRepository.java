package com.EduTech.repository;

import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT NEW com.EduTech.dto.user.UsuarioDTO(u) FROM Usuario u WHERE u.email = :email")
    Optional<UsuarioDTO> findByEmail(String email);

    @Query("SELECT NEW com.EduTech.dto.user.UsuarioDTO(u) FROM Usuario u WHERE u.rut = :rut")
    Optional<UsuarioDTO> findByRut(String rut);

    @Query("SELECT NEW com.EduTech.dto.user.UsuarioDTO(u) FROM Usuario u WHERE u.roles.id = :roleId")
    List<UsuarioDTO> findAllByRoleId(@Param("roleId") Long roleId);

    @Query("select new com.EduTech.dto.user.RespuestaUsuarioDto(u)" +
            " from Usuario u " +
            " where u.roles.id = :idRoles ")
    List<RespuestaUsuarioDto> findAllByIdRoles(@Param("idRoles") Long idRoles);

}
