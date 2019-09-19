package com.example.delivereat.entities;

public class Ubicacion {

    private String provincia;
    private String localidad;
    private String calle;
    private String numero;
    private String piso;
    private String depto;

    public Ubicacion(String provincia, String localidad, String calle, String numero) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public Ubicacion(String provincia, String localidad, String calle, String numero, String piso, String depto) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.depto = depto;
    }

    public String validar(){
        String mensajeValidacion = "";
        if(provincia == null || provincia.isEmpty()) mensajeValidacion = "¡Seleccione una Provincia!";
        else if(localidad == null || localidad.isEmpty()) mensajeValidacion = "¡Seleccione una Localidad!";
        else if (calle == null || calle.isEmpty()) mensajeValidacion = "Ingrese una dirección";
        else if (numero == null || numero.isEmpty()) mensajeValidacion = "Ingrese un número a su dirección";

        if (mensajeValidacion.equals("")) return "OK";
        else return mensajeValidacion;
    }

}
