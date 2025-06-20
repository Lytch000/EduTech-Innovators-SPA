package com.EduTech.service;

import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.dto.user.*;
import com.EduTech.model.Curso;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.EduTech.repository.UsuarioRepository;
import com.EduTech.repository.RolesRepository;
import com.EduTech.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */

@Service
public class UsuarioService {
    public List<RespuestaUsuarioDto> getUsers() {
        List<Usuario> users = repository.findAll();

        List<RespuestaUsuarioDto> responseDto = users.stream()
            .map(RespuestaUsuarioDto::new)
            .collect(Collectors.toList());

        return responseDto;
    }

    public RespuestaUsuarioDto createUser(CrearUsuarioDto newUserDto) {
        Roles role = rolesRepository.findById(newUserDto.getRolId())
            .orElseThrow();

        Usuario user = new Usuario();

        user.setBirthDate(newUserDto.getBirthDate());
        user.setEmail(newUserDto.getEmail());
        user.setFirstName(newUserDto.getFirstName());
        user.setLastName(newUserDto.getLastName());
        user.setPhoneNumber(newUserDto.getPhoneNumber());
        user.setRut(newUserDto.getRut());
        user.setPassword(newUserDto.getPassword());
        user.setRoles(role);
        
        Usuario savedUser = repository.save(user);

        return new RespuestaUsuarioDto(savedUser);
    }

    public RespuestaUsuarioDto getOneUser(Long id) {

        System.out.println("id: " + id);

        Usuario user = repository.findById(id).orElseThrow();

        return new RespuestaUsuarioDto(user);
    }

    public RespuestaUsuarioDto updateUser(Long id, ActualizarUsuarioDto userFields) {
        Usuario user = repository.findById(id).orElseThrow();

        String newEmail = userFields.getEmail();
        Long newPhoneNumber = userFields.getPhoneNumber();
        Long newRoleId = userFields.getRoleId();

        if (newRoleId != null) {
            Roles role = rolesRepository.findById(newRoleId)
                .orElseThrow();

            if (role == null)
                throw new IllegalArgumentException("Role with ID " + newRoleId + " does not exist.");

            user.setRoles(role);
        }

        if (newEmail != null) {
            if (newEmail.isEmpty() || newEmail.isBlank())
                throw new IllegalArgumentException("Email cannot be empty or blank");

            if (newEmail.equals(user.getEmail()))
                throw new IllegalArgumentException("New email cannot be the same as the current email");

            user.setEmail(newEmail);
        }

        if (newPhoneNumber != null) {
            if (newPhoneNumber <= 0)
                throw new IllegalArgumentException("Phone number must be a positive number");

            else if (newPhoneNumber.equals(user.getPhoneNumber()))
                throw new IllegalArgumentException("New phone number cannot be the same as the current phone number");

            else if (newPhoneNumber.toString().length() < 9 || newPhoneNumber.toString().length() > 12)
                throw new IllegalArgumentException("Phone number must be between 9 and 12 digits long");

            user.setPhoneNumber(newPhoneNumber);
        }

        Usuario updatedUser = repository.save(user);

        RespuestaUsuarioDto responseDto = new RespuestaUsuarioDto(updatedUser);

        return responseDto;
    }


    public String deleteUser(Long id) {
        Usuario user = repository.findById(id).orElseThrow();

        repository.delete(user);

        return "User with ID: " + id + " has been deleted successfully.";
    }

    @Autowired
            private UsuarioRepository repository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private CursoRepository cursoRepository;


    // Obtendre el profesor con los detalles del curso que tenga asignado
    public ProfesorDetalleCursoDTO obtenerDetalleProfesorConCursos(Long idProfesor) {
        Usuario profesor = repository.findById(idProfesor)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (!profesor.getRoles().getNombre().equalsIgnoreCase("Profesor")) {
            throw new RuntimeException("El usuario no es un profesor");
        }

        List<Curso> cursosAsignados = cursoRepository.findByProfesorId(idProfesor);

        List<CursoDTO> nombresCursos = cursosAsignados.stream()
                .map(CursoDTO::new)
                .toList();

        return new ProfesorDetalleCursoDTO(
                profesor.getId(),
                profesor.getFirstName(),
                profesor.getEmail(),
                profesor.getRut(),
                nombresCursos
        );
    }

    public UsuarioDTO login(String email, String password) {
        return repository.findByEmailAndPassword(email, password)
                .map(UsuarioDTO::new)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }

}
