package com.exercicio.exercicio_bancario.dto;

import com.exercicio.exercicio_bancario.exceptions.DateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private long id;
    private String name;
    private String lastName;
    private String email;
    private Date birthday;
    private DocumentCPF document;

    public Client(String name, String lastName, String email, String birthday, String cpf) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;

        setDocument(new DocumentCPF(cpf));
        setBirthday(birthday);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public final void setBirthday(String birthday) {
        if(birthday != null){
            try{
                trySetBirthday(birthday);
            }catch(DateException e){
                System.out.println(e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void trySetBirthday(String birthday) throws DateException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.birthday = formatter.parse(birthday);
    }

    public DocumentCPF getDocument() {
        return document;
    }

    public void setDocument(DocumentCPF document) {
        this.document = document;
    }
}
