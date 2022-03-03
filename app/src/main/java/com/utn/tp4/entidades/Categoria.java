package com.utn.tp4.entidades;

import androidx.annotation.NonNull;

public class Categoria {
    private int idCategoria;
    private String descripcion;

    public Categoria(){

    }

    public Categoria(int idCategoria, String descripcion){
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
    }

    public int getIdCategoria(){
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria){
        this.idCategoria = idCategoria;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    @NonNull
    @Override
    public String toString() {
        return descripcion;
    }
}
