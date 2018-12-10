package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Raza implements Parcelable {
    private Long id_raza;
    private String nombre;
    private Long id_especie;
    private String estado;
    private Long id_usuario;

    public Raza() {
    }

    protected Raza(Parcel in) {
        if (in.readByte() == 0) {
            id_raza = null;
        } else {
            id_raza = in.readLong();
        }
        nombre = in.readString();
        if (in.readByte() == 0) {
            id_especie = null;
        } else {
            id_especie = in.readLong();
        }
        estado = in.readString();
        if (in.readByte() == 0) {
            id_usuario = null;
        } else {
            id_usuario = in.readLong();
        }
    }

    public static final Creator<Raza> CREATOR = new Creator<Raza>() {
        @Override
        public Raza createFromParcel(Parcel in) {
            return new Raza(in);
        }

        @Override
        public Raza[] newArray(int size) {
            return new Raza[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id_raza == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id_raza);
        }
        parcel.writeString(nombre);
        if (id_especie == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id_especie);
        }
        parcel.writeString(estado);
        if (id_usuario == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id_usuario);
        }
    }

    public Long getId_raza() {
        return id_raza;
    }

    public void setId_raza(Long id_raza) {
        this.id_raza = id_raza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId_especie() {
        return id_especie;
    }

    public void setId_especie(Long id_especie) {
        this.id_especie = id_especie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
