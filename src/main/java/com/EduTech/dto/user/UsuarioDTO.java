/** Autor Juan Olguin
 *
 */

package com.EduTech.dto.user;

import java.util.Date;

import com.EduTech.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"estudiantes", "profesor"})
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
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRut(rut);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        return user;
    }

    private Long id, phoneNumber, id_rol_fk;
    private String firstName, lastName, rut, password, email;
    private Date birthDate;

    public UsuarioDTO(String email, String password){
        this.email = email;
        this.password = password;
    }

    public UsuarioDTO (Long id, String firstName, String lastName, String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
