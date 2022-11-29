package com.udistrital.controladorsemaforico.logica;

import com.udistrital.controladorsemaforico.dtos.EstadoInterseccionDTO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private ServerSocket server;
    private Socket cliente;
    private int puerto;
    private boolean conectarActivo;

    private List<InterseccionHilo> intersecciones;

    public InterseccionEstado interseccionEstado;

    public Servidor() {
        puerto = 5000;
        conectarActivo = true;
        intersecciones = new ArrayList<>();
        interseccionEstado = new InterseccionEstado();
    }

    public void conectar() {
        try {
            // Crear el servidor
            server = new ServerSocket(puerto);
            Runnable hiloRecepcionClientes = () -> {
                while(conectarActivo){
                    //Esperar a que alguien se conecte
                    try {
                        cliente = server.accept();
                    } catch (IOException e) {
                        System.err.println("Error aceptando el cliente");
                        throw new RuntimeException(e);
                    }
                    // Alguien se conectó

                    InterseccionHilo c = new InterseccionHilo(cliente, interseccionEstado);
                    intersecciones.add(c);
                    c.start();
                }
            };
            new Thread(hiloRecepcionClientes).start();
        } catch (IOException e) {
            System.err.println("Error en el flujo del servidor");
            desconectar();
            throw new RuntimeException(e);
        }
    }

    public void desconectar() {
        try {
            server.close();
            System.out.println("Conexion terminada");
        } catch (IOException e) {
            System.out.println("Error en la desconexion");
            throw new RuntimeException(e);
        }
    }
    public EstadoInterseccionDTO obtenerEstadoInterseccion(int id) {
        // Todo
        //InterseccionHilo interseccion = intersecciones.get(id);

        return null;
    }

    public List<InterseccionHilo> getIntersecciones() {
        return intersecciones;
    }
}
