package com.exercicio.exercicio_bancario.modelsForForm;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public class FirstAccess {
    private String agency;
    private String account;
    @NotBlank(message = "* Email não pode estar em branco")
    private String email;
    @NotBlank(message = "* CPF não pode estar em branco")
    @CPF(message = "* CPF invalido")
    private String cpf;

    public FirstAccess(String agency, String account, String email, String cpf) {
        this.agency = agency;
        this.account = account;
        this.email = email;
        this.cpf = cpf;
    }

    public String getAgency() {
        return agency;
    }

    public String getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }
}
