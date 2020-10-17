package com.exercicio.exercicio_bancario.repository;

import com.exercicio.exercicio_bancario.dto.Client;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class ClientRepository {
    private final ArrayList<Client> list;
    private int lastId;

    public ClientRepository() {
        list = new ArrayList();
        lastId = 0;
    }

    public ArrayList<Client> getAll(){
        return list;
    }

    public Optional<Client> getById(int id){
        return list.stream().filter(item -> item.getId() == id).findFirst();
    }

    public Client add(Client client){
        client.setId(++lastId);
        list.add(client);
        return client;
    }
}
