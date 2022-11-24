package com.udistrital.controladorsemaforico.dtos;

import java.util.List;

public class EstadoInterseccionDTO {
    private List<Semaforo> semaforos;

    public EstadoInterseccionDTO(List<Semaforo> semaforos) {
        this.semaforos = semaforos;
    }

    public List<Semaforo> getSemaforos() {
        return semaforos;
    }

    public void setSemaforos(List<Semaforo> semaforos) {
        this.semaforos = semaforos;
    }
}
