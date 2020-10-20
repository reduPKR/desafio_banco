package com.exercicio.exercicio_bancario.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class Address {
    private String id;
    @NotBlank(message = "* CEP não pode estar vazio")
    @NotEmpty(message = "* CEP não pode estar vazio")
    private String zip;
    @NotBlank(message = "* Estado não pode estar vazio")
    @NotEmpty(message = "* Estado não pode estar vazio")
    private String state;
    @NotBlank(message = "* Cidade não pode estar vazio")
    @NotEmpty(message = "* Cidade não pode estar vazio")
    private String city;
    @NotBlank(message = "* Bairro não pode estar vazio")
    @NotEmpty(message = "* Bairro não pode estar vazio")
    private String district;
    @NotBlank(message = "* Rua não pode estar vazio")
    @NotEmpty(message = "* Rua não pode estar vazio")
    private String street;
    @NotBlank(message = "* Complmento não pode estar vazio")
    @NotEmpty(message = "* Complmento não pode estar vazio")
    private String complement;

    public Address(String zip, String state, String city, String district, String street, String complement) {
        this.zip = zip;
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.complement = complement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
