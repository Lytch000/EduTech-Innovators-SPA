/** Autor Juan Olguin
 *
 */

package com.EduTech.dto.user;

import java.util.Date;

import com.EduTech.builders.BuilderUsuario;
import com.EduTech.model.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {

    public UsuarioDTO(Usuario user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rut = user.getRut();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
    }

    public Usuario toUser() {
        Usuario user = new BuilderUsuario()
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .rut(rut)
            .password(password)
            .phoneNumber(phoneNumber)
            .birthDate(birthDate)
            .build();

        return user;
    }

    private Long id, phoneNumber, id_rol_fk;
    private String firstName, lastName, rut, password, email;
    private Date birthDate;
}
