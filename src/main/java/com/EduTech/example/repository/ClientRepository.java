package com.EduTech.example.repository;

import com.EduTech.example.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class ClientRepository {

    //Arreglo que guarda todos los clientes
    private List<Client> listClient = new ArrayList<>();

    //Metodo para recuperar todos los clientes
    public List<Client> getAllClient(){
        return listClient;
    }

    //Buscar cliente por id
    public Client getById(int id){
        for (Client client : listClient){
            if(client.getId() == id){
                return client;
            }
        }
        return null;
    }

    //Buscar cliente por rut
    public Client getByRut (String rut){
        for (Client client : listClient){
            if(client.getRut().equals(rut)){
                return client;
            }
        }
        return null;
    }

    //Guardar cliente
    public Client saveClient(Client client){
        listClient.add(client);
        return client;
    }

    //Actualizar cliente
    public Client updateClient(Client client){
        int id = 0;
        int idPosition = 0;

        for (int i = 0; i < listClient.size(); i++) {
            if(listClient.get(i).getId() == client.getId()) {
                id = client.getId();
                idPosition = i;
            }
        }

        Client cli = new Client();
        cli.setId(id);
        cli.setRut(client.getRut());
        cli.setNombre(client.getNombre());
        cli.setEdad(client.getEdad());
        cli.setEmail(client.getEmail());
        cli.setCelular(client.getCelular());

        listClient.set(idPosition, cli);
        return cli;
    }

    //Elimina cliente por id
    public void deleteClient(int id) {
        Client client = getById(id);
        if (client != null) {
            listClient.remove(client);
        }
    }
}
