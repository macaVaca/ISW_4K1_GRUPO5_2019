package com.example.delivereat.entities;

public class Ubicacion {

    private String provincia;
    private String localidad;
    private String calle;
    private String numero;
    private String piso;
    private String depto;
    private String referencia;

    public Ubicacion(String provincia, String localidad, String calle, String numero, String referencia) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
        this.referencia = referencia;
    }

    public Ubicacion(String provincia, String localidad, String calle, String numero, String piso, String depto, String referencia) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.depto = depto;
        this.referencia = referencia;
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
