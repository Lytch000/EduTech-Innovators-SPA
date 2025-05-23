/** Autor Juan Olguin
 *
 */

package com.EduTech.dto;

import com.EduTech.model.Client;
import lombok.Data;

@Data
public class ClientDTO {

    private Long id;
    private String rut;
    private String nombre;
    private int edad;
    private String email;
    private int celular;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.rut = client.getRut();
        this.nombre = client.getNombre();
        this.edad = client.getEdad();
        this.email = client.getEmail();
        this.celular = client.getCelular();
    }
}
