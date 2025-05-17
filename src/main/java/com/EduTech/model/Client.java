package com.EduTech.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private int id;
    private String rut;
    private String nombre;
    private int edad;
    private String email;
    private int celular;

}
