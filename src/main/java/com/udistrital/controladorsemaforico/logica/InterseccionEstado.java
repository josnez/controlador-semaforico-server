package com.udistrital.controladorsemaforico.logica;

public class InterseccionEstado {
    private String estado = "inicializado";
    private String estadoInterseccion1 = "No se ha conectado";
    private String estadoInterseccion2 = "No se ha conectado";
    private String estadoInterseccion3 = "No se ha conectado";

    private String tiempoInterseccion1 = "No se ha conectado";
    private String tiempoInterseccion2 = "No se ha conectado";
    private String tiempoInterseccion3 = "No se ha conectado";

    private String configSemaforo1 = "No se ha conectado";
    private String configSemaforo2 = "No se ha conectado";
    private String configSemaforo3 = "No se ha conectado";

    public void setEstado(String estado, int id) {
        if(id==1)
            this.estadoInterseccion1 = estado;
        if(id==2)
            this.estadoInterseccion2 = estado;
        if(id==3)
            this.estadoInterseccion3 = estado;
        else
            this.estado = "error en index de semaforos, id: "+ id;
    }

    public void setTiempo(String tiempo, int id) {
        if(id==1)
            this.tiempoInterseccion1 = tiempo;
        if(id==2)
            this.tiempoInterseccion2 = tiempo;
        if(id==3)
            this.tiempoInterseccion3 = tiempo;
    }

    public void setConfiguracion(String config, int id) {
        if(id==1)
            this.configSemaforo1 = config;
        if(id==2)
            this.configSemaforo2 = config;
        if(id==3)
            this.configSemaforo3 = config;
    }
    public String getEstado() {
        return estado;
    }

    public String getEstadoInterseccion1() {
        return estadoInterseccion1;
    }

    public String getEstadoInterseccion2() {
        return estadoInterseccion2;
    }

    public String getEstadoInterseccion3() {
        return estadoInterseccion3;
    }

    public String getTiempoInterseccion1() {
        return tiempoInterseccion1;
    }

    public String getTiempoInterseccion2() {
        return tiempoInterseccion2;
    }

    public String getTiempoInterseccion3() {
        return tiempoInterseccion3;
    }

    public String getConfigSemaforo1() {
        return configSemaforo1;
    }

    public String getConfigSemaforo2() {
        return configSemaforo2;
    }

    public String getConfigSemaforo3() {
        return configSemaforo3;
    }
}
