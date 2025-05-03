package com.EduTech.example.service;

import com.EduTech.example.model.Client;
import com.EduTech.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients(){
        return clientRepository.getAllClient();
    }

    public Client saveClient(Client client){
        return clientRepository.saveClient(client);
    }

    public Client getById(int id){
        return clientRepository.getById(id);
    }

    public Client updateClient(Client client){
        return clientRepository.updateClient(client);
    }

    public String deleteClient(int id){
        clientRepository.deleteClient(id);
        return "Cliente eliminado correctamente";
    }
}
