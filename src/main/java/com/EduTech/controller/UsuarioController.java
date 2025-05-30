package com.EduTech.controller;

import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.dto.user.LoginRequest;
import com.EduTech.service.UsuarioService;
import com.EduTech.dto.user.CrearUsuarioDto;
import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.dto.user.ActualizarUsuarioDto;
import com.EduTech.dto.user.ActualizarContraseniaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping()
    public ResponseEntity<List<RespuestaUsuarioDto>> getAllUsers() {
        List<RespuestaUsuarioDto> users = service.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<RespuestaUsuarioDto> createUser(@RequestBody CrearUsuarioDto newUser) {
        RespuestaUsuarioDto entity = service.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RespuestaUsuarioDto> getOneUser(@PathVariable Long id) {
        RespuestaUsuarioDto entity = service.getOneUser(id);
        return ResponseEntity.ok(entity);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody ActualizarUsuarioDto userFields) {
        try {
            RespuestaUsuarioDto entity = service.updateUser(id, userFields);
            return ResponseEntity.ok(entity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            String message = service.deleteUser(id);
            return ResponseEntity.ok(message);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Autor Juan Olguin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UsuarioDTO usuario = service.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    //Autor Victor Garces
    // es el cual me permitira ingresar el id del profesor y me listara los cursos asignados previamente
    @GetMapping("/profesor/detalle/{id}")
    public ResponseEntity<?> obtenerDetalleProfesor(@PathVariable Long id) {
        try {
            ProfesorDetalleCursoDTO detalle = service.obtenerDetalleProfesorConCursos(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
