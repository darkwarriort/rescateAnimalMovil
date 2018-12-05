package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mascota implements Parcelable{
    String especie;
    String raza ;
    String direccion;
    String reporte;

    public Mascota() {
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    protected Mascota(Parcel in) {
        especie = in.readString();
        raza = in.readString();
        direccion = in.readString();
        reporte = in.readString();
    }

    public static final Creator<Mascota> CREATOR = new Creator<Mascota>() {
        @Override
        public Mascota createFromParcel(Parcel in) {
            return new Mascota(in);
        }

        @Override
        public Mascota[] newArray(int size) {
            return new Mascota[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(especie);
        parcel.writeString(raza);
        parcel.writeString(direccion);
        parcel.writeString(reporte);
    }
}
