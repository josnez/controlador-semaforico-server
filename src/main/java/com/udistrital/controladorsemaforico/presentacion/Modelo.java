package com.udistrital.controladorsemaforico.presentacion;

import com.udistrital.controladorsemaforico.logica.InterseccionEstado;
import com.udistrital.controladorsemaforico.logica.Servidor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Modelo {

    private Servidor servidor;

    public Servidor getMiSistema() {
        if(servidor == null){
            servidor = new Servidor();
        }
        return servidor;
    }

    public InterseccionEstado getInterseccionEstado(){
        return servidor.interseccionEstado;
    }

    @PostConstruct
    public void iniciar() {
        System.out.println("Estableciendo conexión...");
        getMiSistema().conectar();
    }

    public EstadoInterseccionDTO obtenerEstadoInterseccion(int id) {
        return getMiSistema().obtenerEstadoInterseccion(id);
    }
}
