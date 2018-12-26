package com.fundacionrescate.rescata.model;

public class Participante {

    private Long id_part;
    private Long id_usuario;
    private Long id_evento;

    public Participante(){

    }

    public Long getId_part() {
        return id_part;
    }

    public void setId_part(Long id_part) {
        this.id_part = id_part;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_evento() {
        return id_evento;
    }

    public void setId_evento(Long id_evento) {
        this.id_evento = id_evento;
    }
}
