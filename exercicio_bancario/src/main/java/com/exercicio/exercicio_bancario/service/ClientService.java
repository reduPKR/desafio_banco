package com.exercicio.exercicio_bancario.service;

import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.exceptions.ClientException;
import com.exercicio.exercicio_bancario.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    public ArrayList<Client> getAll(){
        return repository.getAll();
    }

    public Client getById(int id){
        final Optional<Client> client = repository.getById(id);
        if(client.isPresent()){
            return client.get();
        }
        throw new ClientException();
    }

    public Client add(Client client){
        return repository.add(client);
    }

}
