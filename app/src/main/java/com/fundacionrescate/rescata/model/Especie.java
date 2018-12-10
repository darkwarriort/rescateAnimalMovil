package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Especie implements Parcelable{

    private Long id_especie;
    private String nombre;
    private Long id_usuario;
    private String estado;

    public Especie() {
    }

    protected Especie(Parcel in) {
        if (in.readByte() == 0) {
            id_especie = null;
        } else {
            id_especie = in.readLong();
        }
        nombre = in.readString();
        if (in.readByte() == 0) {
            id_usuario = null;
        } else {
            id_usuario = in.readLong();
        }
        estado = in.readString();
    }

    public static final Creator<Especie> CREATOR = new Creator<Especie>() {
        @Override
        public Especie createFromParcel(Parcel in) {
            return new Especie(in);
        }

        @Override
        public Especie[] newArray(int size) {
            return new Especie[size];
        }
    };

    public Long getIdEspecie() {
        return id_especie;
    }

    public void setIdEspecie(Long idEspecie) {
        this.id_especie = idEspecie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id_especie == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id_especie);
        }
        parcel.writeString(nombre);
        if (id_usuario == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id_usuario);
        }
        parcel.writeString(estado);
    }


    @Override
    public String toString() {
        return nombre;
    }
}
