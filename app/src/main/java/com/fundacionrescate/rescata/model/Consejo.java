package com.fundacionrescate.rescata.model;

public class Consejo {


    String nombre;
    String detalle;
    String estado;
    long  fecha_ingreso;

    public Consejo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(long fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
}
