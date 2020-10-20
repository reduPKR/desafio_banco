package com.exercicio.exercicio_bancario.service;

import com.exercicio.exercicio_bancario.dto.Account;
import com.exercicio.exercicio_bancario.dto.Address;
import com.exercicio.exercicio_bancario.dto.Client;
import com.exercicio.exercicio_bancario.repository.AccountRepository;
import com.exercicio.exercicio_bancario.repository.ClientRepository;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repositoryClient;
    @Autowired
    private AccountRepository repositoryAccount;

    public ArrayList<Client> getAll(){
        return repositoryClient.getAll();
    }

    public Client getById(int id){
        final Optional<Client> client = repositoryClient.getById(id);
        if(client.isPresent()){
            return client.get();
        }
        return null;
    }

    public Client getByCPF(String cpf){
        final Optional<Client> client = repositoryClient.getByCPF(cpf);
        if(client.isPresent()){
            return client.get();
        }
        return null;
    }

    public Client getByEmailAndCPF(String email, String cpf) {
        final Optional<Client> client = repositoryClient.getByEmailAndCPF(email,cpf);
        if(client.isPresent()){
            return client.get();
        }
        return null;
    }

    public Client getByAccount(Account account){
        final Optional<Client> client = repositoryClient.getByAccount(account);
        if(client.isPresent()){
            return client.get();
        }
        return null;
    }

    public Client add(Client client){
        if(uniqueCPF(client.getDocument().getCpf())){
            return repositoryClient.add(client);
        }
        return null;
    }

    private Boolean uniqueCPF(String cpf){
        Optional<Client> client = repositoryClient.getByCPF(cpf);
        if(client.isPresent())
            return false;
        return true;
    }

    public Client saveImage(MultipartFile image, String cpf) {
        try {
            return trySaveImage(image, cpf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Client trySaveImage(MultipartFile image, String cpf) throws IOException {
        Optional<Client> client = repositoryClient.getByCPF(cpf);
        if(client.isPresent()){
            byte[] bytes = image.getBytes();
            client.get().getDocument().setImage(bytes);
            client.get().getDocument().setExtension(getExtension(image));
            return client.get();
        }
        return null;
    }

    private String getExtension(MultipartFile image){
        String imageAux = image.getOriginalFilename();
        String[] extension = imageAux.split("\\.");
        return extension[(extension.length-1)];
    }

    public void mountImage(byte[] bytes, String extension){
        try {
            tryMountImage(bytes, extension);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryMountImage(byte[] bytes, String extension) throws IOException {
        String pathToFile = getBasePathImage();
        rebootDirectory(new File(pathToFile));

        Path path = Paths.get(pathToFile+"\\cpf."+extension);
        Files.write(path, bytes);
    }

    private void rebootDirectory (File file) {
        removeDirectory(file);
        createDirectory(file);
    }

    private void removeDirectory(File file){
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; ++i) {
                removeDirectory(files[i]);
            }
        }
        file.delete();
    }

    private void createDirectory(File file){
        file.mkdirs();
    }

    public String getBasePathImage(){
        return System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image";
    }

    public boolean addAddress(Address address, String cpf) {
        Optional<Client> client = repositoryClient.getByCPF(cpf);
        if(client.isPresent()){
            client.get().setAddress(address);
            return true;
        }
        return false;
    }

    public Account createAccount() {
        return repositoryAccount.generateAccount();
    }

    public Account search(String agency, String account) {
        Optional<Account> response = repositoryAccount.search(agency, account);
        if(response.isPresent()){
            return response.get();
        }
        return null;
    }

}
