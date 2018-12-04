package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Reporte implements Parcelable {

    private Long idReporte;
    private String nombre;
    private Long idEspecie;
    private Long idRaza;
    private String edad;
    private Long idSexo;
    private String color;
    private String telefono;
    private String direccion;
    private String coordenadas;
    private float latitud;
    private float longitud;
    private String foto;
    private String estadoAnimal;
    private Date fechaModificacion;
    private Date fecha;
    private String estado;

    protected Reporte(Parcel in) {
        if (in.readByte() == 0) {
            idReporte = null;
        } else {
            idReporte = in.readLong();
        }
        nombre = in.readString();
        if (in.readByte() == 0) {
            idEspecie = null;
        } else {
            idEspecie = in.readLong();
        }
        if (in.readByte() == 0) {
            idRaza = null;
        } else {
            idRaza = in.readLong();
        }
        edad = in.readString();
        if (in.readByte() == 0) {
            idSexo = null;
        } else {
            idSexo = in.readLong();
        }
        color = in.readString();
        telefono = in.readString();
        direccion = in.readString();
        coordenadas = in.readString();
        latitud = in.readFloat();
        longitud = in.readFloat();
        foto = in.readString();
        estadoAnimal = in.readString();
        estado = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (idReporte == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idReporte);
        }
        parcel.writeString(nombre);
        if (idEspecie == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idEspecie);
        }
        if (idRaza == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idRaza);
        }
        parcel.writeString(edad);
        if (idSexo == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idSexo);
        }
        parcel.writeString(color);
        parcel.writeString(telefono);
        parcel.writeString(direccion);
        parcel.writeString(coordenadas);
        parcel.writeFloat(latitud);
        parcel.writeFloat(longitud);
        parcel.writeString(foto);
        parcel.writeString(estadoAnimal);
        parcel.writeString(estado);
    }

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

    public Long getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Long idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Long getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Long idRaza) {
        this.idRaza = idRaza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Long getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Long idSexo) {
        this.idSexo = idSexo;
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

    public String getEstadoAnimal() {
        return estadoAnimal;
    }

    public void setEstadoAnimal(String estadoAnimal) {
        this.estadoAnimal = estadoAnimal;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
