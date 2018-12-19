package com.fundacionrescate.rescata.model;

import java.util.Date;

public class Evento {

    private Long idEventos;
    private String actividad;
    private String descripcion;
    private String lugar;
    private Date fecha;
    private String turno_1;
    private String turno_2;
    private String estado;

    public Evento(){

    }
    public Long getIdEventos() {
        return idEventos;
    }

    public void setIdEventos(Long idEventos) {
        this.idEventos = idEventos;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTurno_1() {
        return turno_1;
    }

    public void setTurno_1(String turno_1) {
        this.turno_1 = turno_1;
    }

    public String getTurno_2() {
        return turno_2;
    }

    public void setTurno_2(String turno_2) {
        this.turno_2 = turno_2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
