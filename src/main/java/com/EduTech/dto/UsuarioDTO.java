/** Autor Juan Olguin
 *
 */

package com.EduTech.dto;

import com.EduTech.model.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String rut;
    private String nombre;
    private int edad;
    private String email;
    private int celular;
    private Long id_rol_fk;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.rut = usuario.getRut();
        this.nombre = usuario.getNombre();
        this.edad = usuario.getEdad();
        this.email = usuario.getEmail();
        this.celular = usuario.getCelular();
        this.id_rol_fk = usuario.getRoles().getId();
    }
}
