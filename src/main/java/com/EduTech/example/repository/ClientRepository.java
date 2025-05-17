package com.EduTech.example.repository;

import com.EduTech.example.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class ClientRepository {

    //Arreglo que guarda todos los clientes
    private List<Client> listClient = new ArrayList<>();

    //Clientes por defecto
    public ClientRepository(){
        listClient.add(new Client(1, "12.345.678-9", "Juan Perez", 30, "juan.perez@mail.com", 912345678));
        listClient.add(new Client(2, "11.223.344-5", "Maria Lopez", 25, "maria.lopez@mail.com", 987654321));
        listClient.add(new Client(3, "20.101.202-3", "Carlos Ruiz", 40, "carlos.ruiz@mail.com", 923456789));
        listClient.add(new Client(4, "15.789.654-1", "Ana Torres", 35, "ana.torres@mail.com", 934567890));
        listClient.add(new Client(5, "18.456.123-7", "Pedro Gonzalez", 28, "pedro.gonzalez@mail.com", 945678901));
        listClient.add(new Client(6, "16.987.321-2", "Lucía Fernández", 32, "lucia.fernandez@mail.com", 956789012));
        listClient.add(new Client(7, "13.369.258-6", "Andres Soto", 45, "andres.soto@mail.com", 967890123));
        listClient.add(new Client(8, "19.741.852-0", "Sofia Castro", 29, "sofia.castro@mail.com", 978901234));
        listClient.add(new Client(9, "17.852.963-4", "Diego Morales", 38, "diego.morales@mail.com", 989012345));
        listClient.add(new Client(10, "14.963.741-8", "Camila Rojas", 90, "camila.rojas@mail.com", 990123456));
    }

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

    //Buscar cliente por nombre
    public Client getbyName (String name){
        for (Client client : listClient){
            if(client.getNombre().equalsIgnoreCase(name)){
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

    //Calcular cantidad de clientes
    public int totalClient(){
        return listClient.size();
    }

    //Obtener cliente mas joven
    public Client youngerClient(){
        int younger = 0;
        int posYoung = 0;
        for (int i = 0; i < listClient.size(); i++) {
            if ( i == 0 ){
                younger = listClient.get(i).getEdad();
            } else if (listClient.get(i).getEdad() < younger) {
                younger = younger = listClient.get(i).getEdad();
                posYoung = i;
            }
        }

        return listClient.get(posYoung);
    }

    public Client olderClient(){
        int older = 0;
        int posOld = 0;
        for (int i = 0; i < listClient.size(); i++) {
            if ( i == 0 ){
                older = listClient.get(i).getEdad();
            } else if (listClient.get(i).getEdad() > older) {
                older = older = listClient.get(i).getEdad();
                posOld = i;
            }
        }

        return listClient.get(posOld);
    }
}
