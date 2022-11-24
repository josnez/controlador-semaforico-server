package com.udistrital.controladorsemaforico.presentacion;

import com.udistrital.controladorsemaforico.dtos.EstadoInterseccionDTO;
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

    @PostConstruct
    public void iniciar() {
        System.out.println("Estableciendo conexión...");
        getMiSistema().conectar();
    }

    public EstadoInterseccionDTO obtenerEstadoInterseccion(int id) {
        return getMiSistema().obtenerEstadoInterseccion(id);
    }
}
