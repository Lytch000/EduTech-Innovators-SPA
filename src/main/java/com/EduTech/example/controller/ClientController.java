package com.EduTech.example.controller;

import com.EduTech.example.model.Client;
import com.EduTech.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> listClient(){
        return clientService.getClients();
    }

    @PostMapping()
    public Client addClient(@RequestBody Client client){
        return clientService.saveClient(client);
    }

    @GetMapping("{id}")
    public Client searchClient(@PathVariable int id){
        return clientService.getById(id);
    }

    @PutMapping("{id}")
    public Client updateClient(@PathVariable int id, @RequestBody Client client){
        return clientService.updateClient(client);
    }

    @DeleteMapping("{id}")
    public String deleteClient(@PathVariable int id){
        return clientService.deleteClient(id);
    }

}
