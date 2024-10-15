package com.example.trabajofirebase1.Clases;

public class Libro {
    private String idAutor;
    private String nombre;
    private String estado;

    public Libro()
    {
        this.idAutor="";
        this.nombre="";
        this.estado="";
    }

    public Libro(String idAutor, String nombre, String estado )
    {
        this.idAutor=idAutor;
        this.nombre=nombre;
        this.estado=estado;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "idAutor='" + idAutor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

}
