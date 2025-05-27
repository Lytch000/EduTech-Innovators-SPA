package com.EduTech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.EduTech.dto.user.UserDto;
import com.EduTech.model.User;
import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT NEW com.EduTech.dto.user.UserDto(c) FROM User c WHERE c.email = :email")
    Optional<UserDto> findByEmail(String email);
}
