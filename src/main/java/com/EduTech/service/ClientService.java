package com.EduTech.service;

import com.EduTech.dto.ClientDTO;
import com.EduTech.model.Client;
import com.EduTech.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clienteRepository;

    public List<ClientDTO> listar(){

        List<ClientDTO> clientDTO = clienteRepository.buscarTodos();
        return clientDTO;
    }

    public Client addNewClient(Client cliente){
        return clienteRepository.save(cliente);
    }

}
