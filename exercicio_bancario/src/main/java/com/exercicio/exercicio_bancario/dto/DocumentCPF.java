package com.exercicio.exercicio_bancario.dto;

public class DocumentCPF {
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
