package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Raza implements Parcelable {
    private Long idRaza;
    private String nombre;
    private Long idEspecie;
    private String estado;
    private Long idUsuario;

    public Raza() {
    }

    protected Raza(Parcel in) {
        if (in.readByte() == 0) {
            idRaza = null;
        } else {
            idRaza = in.readLong();
        }
        nombre = in.readString();
        if (in.readByte() == 0) {
            idEspecie = null;
        } else {
            idEspecie = in.readLong();
        }
        estado = in.readString();
        if (in.readByte() == 0) {
            idUsuario = null;
        } else {
            idUsuario = in.readLong();
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
        if (idRaza == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idRaza);
        }
        parcel.writeString(nombre);
        if (idEspecie == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idEspecie);
        }
        parcel.writeString(estado);
        if (idUsuario == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idUsuario);
        }
    }

    public Long getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Long idRaza) {
        this.idRaza = idRaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Long idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
