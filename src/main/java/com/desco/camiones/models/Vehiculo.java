package com.desco.camiones.models;

public class Vehiculo {
    private String id;
    private int tipoRegistro;
    private String marca;
    private String submarca;
    private String modelo;
    private String tipo;
    private int anio;
    private String numSerie;
    private int capacidad;
    private int numCompart;
    private int capCompart;
    private String aseguradora;
    private String numPoliza;
    private String vencPoliza;
    private String municipio;
    private String estado;
    private String numCorralon;
    private String munCorralon;

    public Vehiculo(String id, int tipoRegistro, String marca, String submarca, String modelo, String tipo, int año, String numSerie, int capacidad, int numCompart, int capCompart, String aseguradora, String numPoliza, String vencPoliza, String municipio, String estado, String numCorralon, String munCorralon) {
        this.id = id;
        this.tipoRegistro = tipoRegistro;
        this.marca = marca;
        this.submarca = submarca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.anio = año;
        this.numSerie = numSerie;
        this.capacidad = capacidad;
        this.numCompart = numCompart;
        this.capCompart = capCompart;
        this.aseguradora = aseguradora;
        this.numPoliza = numPoliza;
        this.vencPoliza = vencPoliza;
        this.municipio = municipio;
        this.estado = estado;
        this.numCorralon = numCorralon;
        this.munCorralon = munCorralon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(int tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSubmarca() {
        return submarca;
    }

    public void setSubmarca(String submarca) {
        this.submarca = submarca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getNumCompart() {
        return numCompart;
    }

    public void setNumCompart(int numCompart) {
        this.numCompart = numCompart;
    }

    public int getCapCompart() {
        return capCompart;
    }

    public void setCapCompart(int capCompart) {
        this.capCompart = capCompart;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public String getNumPoliza() {
        return numPoliza;
    }

    public void setNumPoliza(String numPoliza) {
        this.numPoliza = numPoliza;
    }

    public String getVencPoliza() {
        return vencPoliza;
    }

    public void setVencPoliza(String vencPoliza) {
        this.vencPoliza = vencPoliza;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumCorralon() {
        return numCorralon;
    }

    public void setNumCorralon(String numCorralon) {
        this.numCorralon = numCorralon;
    }

    public String getMunCorralon() {
        return munCorralon;
    }

    public void setMunCorralon(String munCorralon) {
        this.munCorralon = munCorralon;
    }
}
