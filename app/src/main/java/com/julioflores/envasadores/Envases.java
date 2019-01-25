package com.julioflores.envasadores;

public class Envases {
    private int ids, nopedidos, cantidades, lote, cantidades2, lote2, cantidades3, lote3;
    private String fechacapturas, fechaaprobaciones, fechaasignadas, fechaenvases,etapa1s, productos, tipoenvases, 	personaasignadas;
    public Envases(){

    }

    public Envases(int ids, int nopedidos, int cantidades, int lote, int cantidades2, int lote2, int cantidades3, int lote3,
                   String fechacapturas, String fechaaprobaciones, String fechaenvases, String fechaasignadas, String etapa1s,
                   String productos, String tipoenvases, String 	personaasignadas) {
        this.ids = ids;
        this.nopedidos = nopedidos;
        this.cantidades = cantidades;
        this.fechaenvases=fechaenvases;
        this.productos = productos;
        this.tipoenvases = tipoenvases;
        this.etapa1s = etapa1s;
        this.personaasignadas = personaasignadas;
        this.fechacapturas = fechacapturas;
        this.fechaaprobaciones = fechaaprobaciones;
        this.fechaasignadas = fechaasignadas;
        this.lote = lote;
        this.cantidades2 = cantidades2;
        this.lote2 = lote2;
        this.cantidades3 = cantidades3;
        this.lote3 = lote3;
    }

    public int getCantidades2() {
        return cantidades2;
    }

    public void setCantidades2(int cantidades2) {
        this.cantidades2 = cantidades2;
    }

    public int getLote2() {
        return lote2;
    }

    public void setLote2(int lote2) {
        this.lote2 = lote2;
    }

    public int getCantidades3() {
        return cantidades3;
    }

    public void setCantidades3(int cantidades3) {
        this.cantidades3 = cantidades3;
    }

    public int getLote3() {
        return lote3;
    }

    public void setLote3(int lote3) {
        this.lote3 = lote3;
    }

    public int getLote() { return lote; }

    public void setLote(int lote) { this.lote = lote; }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getFechaCapturas() {
        return fechacapturas;
    }

    public void setFechaCapturas(String fechacapturas) {
        this.fechacapturas = fechacapturas;
    }

    public String getFechaasignadas() {
        return fechaasignadas;
    }

    public void setFechaasignadas(String fechaasignadas) {
        this.fechaasignadas = fechaasignadas;
    }

    public int getNopedidos() {
        return nopedidos;
    }

    public void setNopedidos(int nopedidos) {
        this.nopedidos = nopedidos;
    }

    public int getCantidades() {
        return cantidades;
    }

    public void setCantidades(int cantidades) {
        this.cantidades = cantidades;
    }

    public String getFechaaprobaciones() {
        return fechaaprobaciones;
    }

    public void setFechaaprobaciones(String fechaaprobaciones) {
        this.fechaaprobaciones = fechaaprobaciones;
    }

    public String getFechaenvases() {
        return fechaenvases;
    }

    public void setFechaenvases(String fechaenvases) {
        this.fechaenvases = fechaenvases;
    }

    public String getEtapa1s() {
        return etapa1s;
    }

    public void setEtapa1s(String etapa1s) {
        this.etapa1s = etapa1s;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getTipoenvases() {
        return tipoenvases;
    }

    public void setTipoenvases(String tipoenvases) {
        this.tipoenvases = tipoenvases;
    }

    public String getPersonaasignadas() {
        return personaasignadas;
    }

    public void setPersonaasignadas(String personaasignadas) {
        this.personaasignadas = personaasignadas;
    }

    @Override
    public String toString() {
        return ids + " "+productos + nopedidos  + cantidades + tipoenvases + fechacapturas + fechaaprobaciones + personaasignadas +
                etapa1s + fechaenvases + fechaasignadas + lote + cantidades2 + lote2 + cantidades3 + lote3;
    }

}