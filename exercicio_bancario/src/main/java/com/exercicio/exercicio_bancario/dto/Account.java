package com.exercicio.exercicio_bancario.dto;

public class Account {
    private int id;
    private String agency;
    private String account;
    private String bankCode;
    private Double balance;
    private String password;

    public Account(String agency, String account, String bankCode, Double balance) {
        this.agency = agency;
        this.account = account;
        this.bankCode = bankCode;
        this.balance = balance;
        password = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
