package com.desco.camiones.models;

public class Camion {
    private String marca;
    private String modelo;
    private String id;
    private int año;

    public Camion(String id,String marca,String modelo,int año){
        this.marca = marca;
        this.modelo = modelo;
        this.id = id;
        this.año = año;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }
}
