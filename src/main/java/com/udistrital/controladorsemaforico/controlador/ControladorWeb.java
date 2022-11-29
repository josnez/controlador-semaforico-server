package com.udistrital.controladorsemaforico.controlador;

import com.udistrital.controladorsemaforico.dtos.Semaforo;
import com.udistrital.controladorsemaforico.presentacion.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ControladorWeb {

    @Autowired
    private Modelo modelo;

    @RequestMapping(path = "gpr-semaforico-todos", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Semaforo> info() {

        ArrayList array = new ArrayList<Semaforo>();
        Semaforo semaforo1 = new Semaforo("Semaforo 1");
        semaforo1.stringParser(modelo.getInterseccionEstado().getEstadoInterseccion1());
        semaforo1.setTiempo(modelo.getInterseccionEstado().getTiempoInterseccion1());
        semaforo1.configParser(modelo.getInterseccionEstado().getConfigSemaforo1());

        Semaforo semaforo2 = new Semaforo("Semaforo 2");
        semaforo2.stringParser(modelo.getInterseccionEstado().getEstadoInterseccion2());
        semaforo2.setTiempo(modelo.getInterseccionEstado().getTiempoInterseccion2());
        semaforo2.configParser(modelo.getInterseccionEstado().getConfigSemaforo2());

        Semaforo semaforo3 = new Semaforo("Semaforo 3");
        semaforo3.stringParser(modelo.getInterseccionEstado().getEstadoInterseccion3());
        semaforo3.setTiempo(modelo.getInterseccionEstado().getTiempoInterseccion3());
        semaforo3.configParser(modelo.getInterseccionEstado().getConfigSemaforo3());

        array.add(semaforo1);
        array.add(semaforo2);
        array.add(semaforo3);

        return array;
    }
    @GetMapping("/gpr-semaforico")
    public Semaforo getbyId (@RequestParam int id){
        ArrayList<Semaforo> lista = info();
        return lista.get(id);
     }
}
