package com.fundacionrescate.rescata.model;

public class Sexo  {
    long id_sexo;
    String nombre;
    long id_usuario;
    String estado;

    public Sexo() {
    }

    public Sexo(long idSexo, String nombre, long id_usuario, String estado) {
        this.id_sexo = idSexo;
        this.nombre = nombre;
        this.id_usuario = id_usuario;
        this.estado = estado;
    }

    public long getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(long id_sexo) {
        this.id_sexo = id_sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
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
