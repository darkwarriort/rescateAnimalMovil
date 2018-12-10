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

    private Long id_users;
    private String estado;

    public Usuario() {
    }

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

    public Long getId_users() {
        return id_users;
    }

    public void setId_users(Long id_users) {
        this.id_users = id_users;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (in.readByte() == 0) {
            id_users = null;
        } else {
            id_users = in.readLong();
        }
        estado = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id_usuario == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id_usuario);
        }
        parcel.writeString(nombres);
        parcel.writeString(apellidos);
        parcel.writeString(correo);
        parcel.writeString(telefono);
        parcel.writeString(direccion);
        parcel.writeString(usuario);
        parcel.writeString(contrasena);
        if (id_users == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id_users);
        }
        parcel.writeString(estado);
    }
}
