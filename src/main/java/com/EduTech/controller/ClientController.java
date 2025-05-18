package com.EduTech.controller;

import com.EduTech.dto.ClientDTO;
import com.EduTech.model.Client;
import com.EduTech.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> listar(){
        List<ClientDTO> clientes = clientService.listar();
        if (clientes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientes);
        }

        return ResponseEntity.ok(clientes);
    }

    @PostMapping()
    public Client addNewClient(@RequestBody Client cliente){
        return clientService.addNewClient(cliente);
    }

}
