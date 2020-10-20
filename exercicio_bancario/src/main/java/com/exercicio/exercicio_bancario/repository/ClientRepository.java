package com.exercicio.exercicio_bancario.repository;

import com.exercicio.exercicio_bancario.dto.Account;
import com.exercicio.exercicio_bancario.dto.Client;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class ClientRepository {
    private final ArrayList<Client> listClients;
    private int lastId;

    public ClientRepository() {
        listClients = new ArrayList();
        lastId = 0;
    }

    public ArrayList<Client> getAll(){
        return listClients;
    }

    public Optional<Client> getById(int id){
        return listClients.stream().filter(item -> item.getId() == id).findFirst();
    }

    public Optional<Client> getByCPF(String cpf){
        return listClients.stream().filter(item -> item.getDocument().getCpf().equals(cpf)).findFirst();
    }

    public Optional<Client> getByEmailAndCPF(String email, String cpf) {
        return listClients.stream().filter(item -> item.getEmail().equals(email) &&
                item.getDocument().getCpf().equals(cpf)).findFirst();
    }

    public Optional<Client> getByAccount(Account account) {
        return listClients.stream().filter(item -> item.getAccount() != null &&
                item.getAccount().getAgency().equals(account.getAgency()) &&
                item.getAccount().getAccount().equals(account.getAccount())
        ).findFirst();
    }

    public Client add(Client client){
        client.setId(++lastId);
        listClients.add(client);
        return client;
    }
}
