package com.fundacionrescate.rescata.model;

public class Sexo  {
    long idSexo;
    String nombre;
    long idUsuario;
    String estado;

    public Sexo() {
    }

    public Sexo(long idSexo, String nombre, long idUsuario, String estado) {
        this.idSexo = idSexo;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.estado = estado;
    }

    public long getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(long idSexo) {
        this.idSexo = idSexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
