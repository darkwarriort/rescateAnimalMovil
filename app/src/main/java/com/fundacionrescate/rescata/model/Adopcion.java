package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Adopcion implements Parcelable{


    private Long id;

    private String nombre;
    private int edad;
    private Long idEspecie;
    private Long idRaza;
    private Long idSexo;
    private String descripcion;
    private String foto;
    private String estadoAnimal;
    private Long idUsuario;
    private Date fechaIngreso;
    private Date fechaModificacion;
    private String estado;


    public Adopcion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
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

    public Long getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Long idSexo) {
        this.idSexo = idSexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    protected Adopcion(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        nombre = in.readString();
        edad = in.readInt();
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
        if (in.readByte() == 0) {
            idSexo = null;
        } else {
            idSexo = in.readLong();
        }
        descripcion = in.readString();
        foto = in.readString();
        estadoAnimal = in.readString();
        if (in.readByte() == 0) {
            idUsuario = null;
        } else {
            idUsuario = in.readLong();
        }
        estado = in.readString();
    }

    public static final Creator<Adopcion> CREATOR = new Creator<Adopcion>() {
        @Override
        public Adopcion createFromParcel(Parcel in) {
            return new Adopcion(in);
        }

        @Override
        public Adopcion[] newArray(int size) {
            return new Adopcion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(nombre);
        parcel.writeInt(edad);
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
        if (idSexo == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idSexo);
        }
        parcel.writeString(descripcion);
        parcel.writeString(foto);
        parcel.writeString(estadoAnimal);
        if (idUsuario == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(idUsuario);
        }
        parcel.writeString(estado);
    }
}
