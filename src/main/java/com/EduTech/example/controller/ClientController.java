package com.EduTech.example.controller;

import com.EduTech.example.model.Client;
import com.EduTech.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

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

    @GetMapping("/searchRut/{rut}")
    public Client seachRutClient(@PathVariable String rut){
        return clientService.getByRut(rut);
    }

    @GetMapping("/searchName/{name}")
    public Client seachNameClient(@PathVariable String name){
        Client cliente = clientService.getByName(name);
        if(cliente != null){
            return cliente;
        }else{
            throw new HttpStatusCodeException(HttpStatus.NOT_FOUND, "Cliente no encontrado") {
            };
        }
    }

    @PutMapping("{id}")
    public Client updateClient(@PathVariable int id, @RequestBody Client client){
        return clientService.updateClient(client);
    }

    @DeleteMapping("{id}")
    public String deleteClient(@PathVariable int id){
        return clientService.deleteClient(id);
    }

    @GetMapping("/total")
    public int totalClient(){
        return clientService.totalClient();
    }

    @GetMapping("/menor")
    public Client youngClient(){
        return clientService.youngerClient();
    }

    @GetMapping("mayor")
    public Client olderClient(){
        return clientService.olderClient();
    }

}
