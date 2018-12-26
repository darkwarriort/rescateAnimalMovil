package com.fundacionrescate.rescata.model;

public class Postulantes {

    private Long id_post;
    private Long id_usuario;
    private Long id_adopcion;

    public Postulantes(){

    }

    public Long getId_post() {
        return id_post;
    }

    public void setId_post(Long id_post) {
        this.id_post = id_post;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_adopcion() {
        return id_adopcion;
    }

    public void setId_adopcion(Long id_adopcion) {
        this.id_adopcion = id_adopcion;
    }
}
