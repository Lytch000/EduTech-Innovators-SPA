/** Autor Juan Olguin
 *
 */

package com.EduTech.repository;

import com.EduTech.dto.ClientDTO;
import com.EduTech.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("Select distinct new com.EduTech.dto.ClientDTO(c)" +
    "from Client c ")
    List<ClientDTO> buscarTodos();

}
