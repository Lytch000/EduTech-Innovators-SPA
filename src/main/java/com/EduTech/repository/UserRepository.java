package com.EduTech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EduTech.dto.user.UserDto;
import com.EduTech.model.User;

import java.util.List;
import java.util.Optional;




/**
 * UserRepository is a Spring Data JPA repository interface for managing User entities.
 *
 * @author Franco Carrasco
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT NEW com.EduTech.dto.user.UserDto(u) FROM User u WHERE u.email = :email")
    Optional<UserDto> findByEmail(String email);

    @Query("SELECT NEW com.EduTech.dto.user.UserDto(u) FROM User u WHERE u.rut = :rut")
    Optional<User> findByRut(String rut);

    @Query("SELECT NEW com.EduTech.dto.user.UserDto(u) FROM User u WHERE u.roles.id = :roleId")
    List<UserDto> findAllByRoleId(@Param("roleId") Long roleId);
}
