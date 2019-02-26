package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable{


    private Long id_usuario;

    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String direccion;
    private String usuario;
    private String contrasena;

    private String estado;
    private Long id_grupo;
    public Usuario() {
    }

    protected Usuario(Parcel in) {
        if (in.readByte() == 0) {
            id_usuario = null;
        } else {
            id_usuario = in.readLong();
        }
        nombres = in.readString();
        apellidos = in.readString();
        correo = in.readString();
        telefono = in.readString();
        direccion = in.readString();
        usuario = in.readString();
        contrasena = in.readString();
        estado = in.readString();
        if (in.readByte() == 0) {
            id_grupo = null;
        } else {
            id_grupo = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id_usuario == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_usuario);
        }
        dest.writeString(nombres);
        dest.writeString(apellidos);
        dest.writeString(correo);
        dest.writeString(telefono);
        dest.writeString(direccion);
        dest.writeString(usuario);
        dest.writeString(contrasena);
        dest.writeString(estado);
        if (id_grupo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_grupo);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(Long id_grupo) {
        this.id_grupo = id_grupo;
    }
}
