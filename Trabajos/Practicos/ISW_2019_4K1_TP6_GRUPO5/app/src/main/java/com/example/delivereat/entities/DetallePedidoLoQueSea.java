package com.example.delivereat.entities;

import android.graphics.Bitmap;
import android.media.Image;

public class DetallePedidoLoQueSea {
    private Bitmap imagen;
    private String nombre;
    private int unidad;
    private float precioUnitario;
    private float cantidad; //Porque puede ser un kilo y medio


    public DetallePedidoLoQueSea(String nombre, float precioUnitario, Float cantidad, int unidad){
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public DetallePedidoLoQueSea(String nombre, float precioUnitario, Float cantidad, int unidad, Bitmap imagen){
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public float getPrecioFinal(){
        return precioUnitario * cantidad;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad(){
        switch (unidad){
            case 0:
                return "Unidades";
            case 1:
                return "Kilogramos";
            case 2:
                return "Metros";
            default:
                return "Unidades";
        }
    }
}
