package com.exercicio.exercicio_bancario.modelsForForm;

import org.hibernate.validator.constraints.NotBlank;

public class Password {
    private String cpf;
    @NotBlank(message = "* A senha não pode estar vazia")
    private String password;

    public Password(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }
}
