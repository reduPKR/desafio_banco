package com.exercicio.exercicio_bancario.repository;

import com.exercicio.exercicio_bancario.dto.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Repository
public class AccountRepository {
    private final ArrayList<Account> listAccounts;
    private int lastId;

    public AccountRepository() {
        listAccounts = new ArrayList();
        lastId = 0;
    }

    public ArrayList<Account> getAll(){
        return listAccounts;
    }

    public Optional<Account> getById(int id){
        return listAccounts.stream().filter(item -> item.getId() == id).findFirst();
    }

    public Account generateAccount(){
        String agency = sertValues(4);
        String accout = sertValues(8);
        listAccounts.add(new Account(agency,accout, "123", 0.0));
        return listAccounts.get(listAccounts.size()-1);
    }

    private String sertValues(int qtde) {
        String value = "";

        Random gerador = new Random();
        for (int i = 0; i < qtde; i++) {
            value = value + Integer.toString(gerador.nextInt(10));
        }
        return value;
    }

    public Optional<Account> search(String agency, String account) {
        return listAccounts.stream().filter(item -> item.getAgency().equals(agency) && item.getAccount().equals(account)).findFirst();
    }
}
