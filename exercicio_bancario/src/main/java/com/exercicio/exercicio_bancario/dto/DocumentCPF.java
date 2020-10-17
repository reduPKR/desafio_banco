package com.exercicio.exercicio_bancario.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public class DocumentCPF {
    @NotBlank(message = "* CPF não pode estar vazio")
    @CPF(message = "* CPF inválido")
    private String cpf;
    private byte[] image;

    public DocumentCPF(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
