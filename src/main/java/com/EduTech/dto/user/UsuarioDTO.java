/** Autor Juan Olguin
 *
 */

package com.EduTech.dto.user;

import java.util.Date;

import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties({"estudiantes", "profesor"})
@Schema(description = "DTO para transferir datos de usuario")
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
        user.setId(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRut(rut);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setBirthDate(birthDate);
        return user;
    }

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Schema(description = "Número de teléfono del usuario", example = "987654321")
    private Long phoneNumber;

    @Schema(description = "ID del rol asociado", example = "2")
    private Long id_rol_fk;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastName;

    @Schema(description = "RUT del usuario", example = "12345678-9")
    private String rut;

    @Schema(description = "Contraseña del usuario")
    private String password;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
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
