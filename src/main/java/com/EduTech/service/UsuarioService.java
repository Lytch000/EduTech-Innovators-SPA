package com.EduTech.service;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.EduTech.repository.CursoRepository;
import com.EduTech.repository.RolesRepository;
import com.EduTech.repository.UsuarioRepository;
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

    public String changePassword(ActualizarContraseniaDto fields) {
        String email = fields.getEmail();
        String oldPassword = fields.getOldPassword();

        UsuarioDTO user = repository.findByEmail(email).orElseThrow();

        String currentUserPassword = user.getPassword();

        if (!currentUserPassword.equals(oldPassword)) 
            throw new IllegalArgumentException("Old password does not match the current password.");
        else if (currentUserPassword.equals(fields.getNewPassword())) 
            throw new IllegalArgumentException("New password cannot be the same as the old password.");

        user.setPassword(fields.getNewPassword());

        repository.save(user.toUser());

        return "Password for user with email: " + email + " has been changed successfully.";
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

    public List<UsuarioDTO> listar(){

        List<UsuarioDTO> usuarioDTO = usuarioRepository.buscarTodos();
        return usuarioDTO;
    }

    public String addNewUsuario(UsuarioDTO dto){

        Roles rol = rolesRepository.findById(dto.getId_rol_fk())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setNombre(dto.getNombre());
        usuario.setEdad(dto.getEdad());
        usuario.setEmail(dto.getEmail());
        usuario.setCelular(dto.getCelular());
        usuario.setRoles(rol);

        usuarioRepository.save(usuario);

        return "Usuario registrado correctamente";

    }

    // Obtendre el profesor con los detalles del curso que tenga asignado
    public ProfesorDetalleCursoDTO obtenerDetalleProfesorConCursos(Long idProfesor) {
        Usuario profesor = usuarioRepository.findById(idProfesor)
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
                profesor.getNombre(),
                profesor.getEmail(),
                profesor.getRut(),
                nombresCursos
        );
    }

    // Esto me permitira actualizar
    public void reemplazarCursosDeProfesor(Long idProfesor, List<Long> idsCursos) {
        Usuario profesor = usuarioRepository.findById(idProfesor)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (!"Profesor".equalsIgnoreCase(profesor.getRoles().getNombre())) {
            throw new RuntimeException("El usuario no tiene rol de Profesor");
        }

        // Eliminar asignaciones actuales
        List<Curso> actuales = cursoRepository.findByProfesorId(idProfesor);
        actuales.forEach(curso -> curso.setProfesor(null));
        cursoRepository.saveAll(actuales);

        // Asignar los nuevos cursos
        for (Long idCurso : idsCursos) {
            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + idCurso));
            curso.setProfesor(profesor);
            cursoRepository.save(curso);
        }
    }

    public void modificarCursosDeProfesor(Long idProfesor, CursoPatchDTO dto) {
        Usuario profesor = usuarioRepository.findById(idProfesor)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (!"Profesor".equalsIgnoreCase(profesor.getRoles().getNombre())) {
            throw new RuntimeException("El usuario no es un profesor");
        }

        // Agregar cursos
        if (dto.getIdsAgregar() != null && !dto.getIdsAgregar().isEmpty()) {
            cursoRepository.findAllById(dto.getIdsAgregar()).forEach(curso -> {
                curso.setProfesor(profesor);
                cursoRepository.save(curso);
            });
        }

        // Eliminar cursos
        if (dto.getIdsEliminar() != null && !dto.getIdsEliminar().isEmpty()) {
            cursoRepository.findAllById(dto.getIdsEliminar()).forEach(curso -> {
                if (curso.getProfesor() != null && curso.getProfesor().getId().equals(idProfesor)) {
                    curso.setProfesor(null);
                    cursoRepository.save(curso);
                }
            });
        }
    }


}
