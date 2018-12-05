package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Especie implements Parcelable{

    private Long idEspecie;
    private String nombre;
    private Long idUsuario;
    private String estado;

    public Especie() {
    }

    protected Especie(Parcel in) {
        if (in.readByte() == 0) {
            idEspecie = null;
        } else {
            idEspecie = in.readLong();
        }
        nombre = in.readString();
        if (in.readByte() == 0) {
            idUsuario = null;
        } else {
            idUsuario = in.readLong();
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
        return idEspecie;
    }

    public void setIdEspecie(Long idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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
        if (idEspecie == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idEspecie);
        }
        parcel.writeString(nombre);
        if (idUsuario == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idUsuario);
        }
        parcel.writeString(estado);
    }


    @Override
    public String toString() {
        return nombre;
    }
}
