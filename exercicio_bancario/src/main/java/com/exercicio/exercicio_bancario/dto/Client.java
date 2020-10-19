package com.exercicio.exercicio_bancario.dto;

import com.exercicio.exercicio_bancario.exceptions.DateException;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import java.time.LocalDate;


public class Client {
    private long id;
    @NotEmpty(message = "* Nome não pode estar vazio.")
    private String name;
    @NotEmpty(message = "* Sobrenome não pode estar vazio.")
    private String lastName;
    @NotEmpty(message = "* E-mail não pode estar vazio.")
    @Email(message = "* E-mail inválido")
    private String email;
    @Range(min=18, message = "* Idade inferior a 18 anos")
    private int age;
    private String birthday;
    @Valid
    private DocumentCPF document = null;
    private Address address = null;

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

    public String getBirthday() {
        return birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public final void setBirthday(String birthday)  {
        if(birthday != null){
            if(validateAge(birthday)){
                this.birthday = birthday;
            }
        }
    }

    private Boolean validateAge(String birthday){
        LocalDate today = LocalDate.now();
        int[] data = convetBirthDay(birthday);

        setAge((today.getYear() - data[0]));
        if(today.getYear() - data[0]  > 18)
            return true;
        if(today.getYear() - data[0] == 18){
            if(data[1] < today.getMonth().getValue()){
                return true;
            }
            if(data[1] == today.getMonth().getValue()){
                if(data[2] <= today.getDayOfMonth()){
                    return true;
                }
            }
        }

        return false;
    }

    private int[] convetBirthDay(String birthday){
        String[] data = birthday.split("-");
        int[] result = new int[3];
        for (int i = 0; i < 3; i++){
            result[i] = Integer.parseInt(data[i]);
        }
        return  result;
    }

    public DocumentCPF getDocument() {
        return document;
    }

    public void setDocument(DocumentCPF document) {
        this.document = document;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
