package com.EduTech.dto.roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class RolRequest {

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción del rol no puede estar vacía")
    private String descripcion;

    @NotNull(message = "La fecha de creación no puede estar vacía")
    private Date fechaCreacion;

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}