package com.utn.tp4.entidades;

public class Articulo {
    private int idArticulo;
    private String nombre;
    private int stock;
    private int idCategoria;
    private String descripcionCategoria;

    public Articulo(){

    }

    public int getIdArticulo(){
        return this.idArticulo;
    }

    public void setIdArticulo(int idArticulo){
        this.idArticulo = idArticulo;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getStock(){
        return this.stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public int getIdCategoria(){
        return this.idCategoria;
    }

    public void setIdCategoria(int idCategoria){
        this.idCategoria = idCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

}
