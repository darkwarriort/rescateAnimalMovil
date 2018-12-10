package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Reporte implements Parcelable {

    private Long idReporte;
    private String nombre;
    private Long id_especie;
    private Long id_raza;
    private String edad;
    private Long id_sexo;
    private String color;
    private String telefono;
    private String direccion;
    private String coordenadas;
    private float latitud;
    private float longitud;
    private String foto;
    private String estado_animal;
//    private long fecha_modificacion;
//    private long fecha;
    private String estado;

    private String especie;
    private String raza;

    public Reporte() {
    }


    protected Reporte(Parcel in) {
        if (in.readByte() == 0) {
            idReporte = null;
        } else {
            idReporte = in.readLong();
        }
        nombre = in.readString();
        if (in.readByte() == 0) {
            id_especie = null;
        } else {
            id_especie = in.readLong();
        }
        if (in.readByte() == 0) {
            id_raza = null;
        } else {
            id_raza = in.readLong();
        }
        edad = in.readString();
        if (in.readByte() == 0) {
            id_sexo = null;
        } else {
            id_sexo = in.readLong();
        }
        color = in.readString();
        telefono = in.readString();
        direccion = in.readString();
        coordenadas = in.readString();
        latitud = in.readFloat();
        longitud = in.readFloat();
        foto = in.readString();
        estado_animal = in.readString();
        estado = in.readString();
        especie = in.readString();
        raza = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idReporte == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idReporte);
        }
        dest.writeString(nombre);
        if (id_especie == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_especie);
        }
        if (id_raza == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_raza);
        }
        dest.writeString(edad);
        if (id_sexo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_sexo);
        }
        dest.writeString(color);
        dest.writeString(telefono);
        dest.writeString(direccion);
        dest.writeString(coordenadas);
        dest.writeFloat(latitud);
        dest.writeFloat(longitud);
        dest.writeString(foto);
        dest.writeString(estado_animal);
        dest.writeString(estado);
        dest.writeString(especie);
        dest.writeString(raza);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reporte> CREATOR = new Creator<Reporte>() {
        @Override
        public Reporte createFromParcel(Parcel in) {
            return new Reporte(in);
        }

        @Override
        public Reporte[] newArray(int size) {
            return new Reporte[size];
        }
    };

    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
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

    public Long getId_raza() {
        return id_raza;
    }

    public void setId_raza(Long id_raza) {
        this.id_raza = id_raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Long getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(Long id_sexo) {
        this.id_sexo = id_sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstado_animal() {
        return estado_animal;
    }

    public void setEstado_animal(String estado_animal) {
        this.estado_animal = estado_animal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
}
