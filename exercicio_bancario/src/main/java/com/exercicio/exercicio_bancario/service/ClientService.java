package com.exercicio.exercicio_bancario.service;

import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.dto.DocumentCPF;
import com.exercicio.exercicio_bancario.exceptions.ClientException;
import com.exercicio.exercicio_bancario.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.io.IOException;
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
        if(uniqueCPF(client.getDocument().getCpf())){
            return repository.add(client);
        }
        return null;
    }

    private Boolean uniqueCPF(String cpf){
        Optional<DocumentCPF> document = repository.getByCPF(cpf);
        if(document.isPresent())
            return false;
        return true;
    }

    public void saveImage(MultipartFile image) {
        try {
            trySaveImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void trySaveImage(MultipartFile image) throws IOException {
        String folder = "/images/";
        byte[] bytes = image.getBytes();

    }
}
