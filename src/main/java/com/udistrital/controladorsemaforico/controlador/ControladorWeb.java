package com.udistrital.controladorsemaforico.controlador;

import com.udistrital.controladorsemaforico.dtos.EstadoInterseccionDTO;
import com.udistrital.controladorsemaforico.dtos.Semaforo;
import com.udistrital.controladorsemaforico.presentacion.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorWeb {

    @Autowired
    private Modelo modelo;

    @RequestMapping(path = "gpr-semaforico", method = RequestMethod.GET)
    public EstadoInterseccionDTO info(@RequestParam(required = true) Integer id) {
        List array = new ArrayList<>();
        array.add(new Semaforo("" + modelo.getMiSistema().getIntersecciones().size(), "azul"));
        return new EstadoInterseccionDTO(array);
    }
}
