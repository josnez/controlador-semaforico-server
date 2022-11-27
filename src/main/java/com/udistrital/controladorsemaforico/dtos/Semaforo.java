package com.udistrital.controladorsemaforico.dtos;

import java.util.ArrayList;

public class Semaforo {

    private String nombre;

    private ArrayList<String> color;
    private String tiempo;

    public Semaforo(String nombre) {
        this.nombre = nombre;
    }
    public void stringParser(String data){
        // byte recibido tarjeta-|i|r|a|v|i|r|a|v|
        String[] datos = data.split("-");
        ArrayList colores = new ArrayList<String>();
        for (int i = 0; i < datos.length; i++) {
            String[] coloresCode = datos[i].split(":");
            datos[i] = coloresCode[1];
            String color1 = datos[i].substring(0,4);
            String color2 = datos[i].substring(4,8);
            colores.add(getColorName(color1));
            colores.add(getColorName(color2));
        }

        setColor(colores);
    }
    private String getColorName(String colorcode){

        switch (colorcode){
            case "0001":
                return "verde - #88c057";
            case "0010":
                return "amarillo - #fdca66";
            case "0100":
                return "rojo - #ed7161";
            case "1001":
                return "verde intermitente - #50782d";
            case "1010":
                return "amarillo-intermitente";
            case "1100":
                return "rojo-intermitente";
            case "0110":
                return "rojo-amarillo - #f59e63";
            default:
                return "no-color";
        }
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> colores) {
        this.color = colores;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
