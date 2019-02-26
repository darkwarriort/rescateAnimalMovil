package com.fundacionrescate.rescata.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Adopcion
        implements Parcelable{


    private Long id;

    private String nombre;
    private int edad;
    private Long id_especie;
    private Long id_raza;
    private Long id_sexo;
    private String descripcion;
    private String foto;
    private String estado_animal;
    private Long id_usuario;
    private Date fecha_ingreso;
    private Date fecha_modificacion;
    private String estado;
    private String adoptado_por;
    private String observacion;



    public Adopcion() {
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
            id_especie = null;
        } else {
            id_especie = in.readLong();
        }
        if (in.readByte() == 0) {
            id_raza = null;
        } else {
            id_raza = in.readLong();
        }
        if (in.readByte() == 0) {
            id_sexo = null;
        } else {
            id_sexo = in.readLong();
        }
        descripcion = in.readString();
        foto = in.readString();
        estado_animal = in.readString();
        if (in.readByte() == 0) {
            id_usuario = null;
        } else {
            id_usuario = in.readLong();
        }
        estado = in.readString();
        adoptado_por = in.readString();
        observacion = in.readString();
        estado = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(nombre);
        dest.writeInt(edad);
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
        if (id_sexo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_sexo);
        }
        dest.writeString(descripcion);
        dest.writeString(foto);
        dest.writeString(estado_animal);
        if (id_usuario == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_usuario);
        }
        dest.writeString(estado);
        dest.writeString(adoptado_por);
        dest.writeString(observacion);
        dest.writeString(estado);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Long getId_sexo() {
        return id_sexo;
    }

    public void setId_sexo(Long id_sexo) {
        this.id_sexo = id_sexo;
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

    public String getEstado_animal() {
        return estado_animal;
    }

    public void setEstado_animal(String estado_animal) {
        this.estado_animal = estado_animal;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAdoptado_por() {
        return adoptado_por;
    }

    public void setAdoptado_por(String adoptado_por) {
        this.adoptado_por = adoptado_por;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
