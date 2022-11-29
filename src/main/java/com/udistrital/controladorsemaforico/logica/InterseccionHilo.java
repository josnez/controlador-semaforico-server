package com.udistrital.controladorsemaforico.logica;

import org.json.simple.JSONArray;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InterseccionHilo extends Thread {

    private Socket host;
    public DataOutputStream datosSalida;
    private DataInputStream datosEntrada;
    private ReadJSON readJSON;
    private ArrayList<ArrayList<int[]>> allCardsLeds;
    private ArrayList<String> infoCod;
    private Map<Integer, String> intersecciones;
    private String infoRutina;
    private boolean dano;
    private int idInterseccion;
    public static int numIntersecciones = 0;

    private InterseccionEstado interseccionEstado;

    public InterseccionHilo(Socket c, InterseccionEstado ie) {
        host = c;
        interseccionEstado = ie;
        numIntersecciones++;
        idInterseccion = numIntersecciones;
        readJSON = new ReadJSON();
        allCardsLeds = new ArrayList<>();
        infoCod = new ArrayList<>();
        intersecciones = new HashMap<>() {{
            put(1,"src/main/resources/static/datos/data13-41.json");
            put(2,"src/main/resources/static/datos/data34-38.json");
            put(3,"src/main/resources/static/datos/data80-43.json");
        }};
    }

    public String leerConfiguracion() {
        // Leer configuracion
        String config = "";
        try {
            readJSON.readFile(intersecciones.get(idInterseccion));
            config = readJSON.getPrimerMensaje();
            interseccionEstado.setConfiguracion(config, idInterseccion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }

    private boolean validarFuncionamientoDeLeds(String cantLedsFuncionando) {
        String[] ledsXTarjetas = cantLedsFuncionando.split("/");
        for (int i = 0; i < ledsXTarjetas.length; i++) {
            ledsXTarjetas[i] = ledsXTarjetas[i].replaceAll("-", "");
            ledsXTarjetas[i] = ledsXTarjetas[i].replaceAll(":", "");
            if (!limpiarNroLedsFuncionandoXTarjeta(infoCod.get(i)).equals(ledsXTarjetas[i])) {
                System.out.println(limpiarNroLedsFuncionandoXTarjeta(infoCod.get(i)) + " " + ledsXTarjetas[i]);
                return false;
            }
        }
        return true;
    }

    private String limpiarNroLedsFuncionandoXTarjeta(String cantLedsXTarjeta) {
        String nroLedsFiltrado = cantLedsXTarjeta.substring(3);
        StringBuffer sB = new StringBuffer(nroLedsFiltrado).deleteCharAt(3);
        return sB.toString();
    }

    public void enviarRutinaConexion(String seg) {
        ArrayList<JSONArray> arr = readJSON.getRutinaConexion();
        String dato = "";

        for (int i = 0; i < arr.size(); i++) {
            Iterator iterator = arr.get(i).iterator();
            String cod = buscarCodigo(iterator, seg);
            if (!"".equals(cod)) {
                cod = cod.replaceAll("^(\\d*)", Integer.toString(i + 1));
                if (infoCod.size() >= i+1) {
                    infoCod.set(i, cod);
                } else {
                    infoCod.add(cod);
                }
            }

            if (i == 0) {
                dato += infoCod.get(i);
            } else {
                dato += "-" + infoCod.get(i);
            }
        }

        if (!dato.equals("")) {
            infoRutina = dato;
        }

        // Enviar y recibir
        String cantLedsFuncionando;
        try {
            datosSalida.writeUTF(infoRutina);
            interseccionEstado.setEstado(infoRutina, idInterseccion);
        } catch (IOException e) {
            System.out.println("Error en el envio de la rutina de conexion");
            throw new RuntimeException(e);
        }

        try {
            cantLedsFuncionando = datosEntrada.readUTF();
            if (!validarFuncionamientoDeLeds(cantLedsFuncionando)) {
                System.out.println("Led danado");
            }
        } catch (IOException e) {
            System.out.println("Error en el recaudo de los leds funcionando");
            throw new RuntimeException(e);
        }
    }

    public void enviarRutinaNormal(String seg) {
        ArrayList<JSONArray> arr = readJSON.getRutinaPlan();
        String dato = "";

        for (int i = 0; i < arr.size(); i++) {
            Iterator iterator = arr.get(i).iterator();
            String cod = buscarCodigo(iterator, seg);
            if (!"".equals(cod)) {
                cod = cod.replaceAll("^(\\d*)", Integer.toString(i + 1));
                if (infoCod.size() >= i+1) {
                    infoCod.set(i, cod);
                } else {
                    infoCod.add(cod);
                }
            }

            if (i == 0) {
                dato += infoCod.get(i);
            } else {
                dato += "-" + infoCod.get(i);
            }
        }

        if (!dato.equals("")) {
            infoRutina = dato;
        }

        // Enviar y recibir
        String cantLedsFuncionando;
        try {
            datosSalida.writeUTF(infoRutina);
            interseccionEstado.setEstado(infoRutina, idInterseccion);
        } catch (IOException e) {
            System.out.println("Error en el envio de la rutina normal");
            throw new RuntimeException(e);
        }

        try {
            cantLedsFuncionando = datosEntrada.readUTF();
            if (!validarFuncionamientoDeLeds(cantLedsFuncionando)) {
                System.out.println("Led danado");
            }
        } catch (IOException e) {
            System.out.println("Error en el recaudo de los leds funcionando");
            throw new RuntimeException(e);
        }
    }

    public void enviarRutinaDesconexion(String seg) {
        ArrayList<JSONArray> arr = readJSON.getRutinaDesconexion();
        String dato = "";

        for (int i = 0; i < arr.size(); i++) {
            Iterator iterator = arr.get(i).iterator();
            String cod = buscarCodigo(iterator, seg);
            if (!"".equals(cod)) {
                cod = cod.replaceAll("^(\\d*)", Integer.toString(i + 1));
                if (infoCod.size() >= i+1) {
                    infoCod.set(i, cod);
                } else {
                    infoCod.add(cod);
                }
            }
            if (i == 0) {
                dato += infoCod.get(i);
            } else {
                dato += "-" + infoCod.get(i);
            }
        }
        if (!dato.equals("")) {
            infoRutina = dato;
        }
        // Enviar y recibir
        String cantLedsFuncionando;
        try {
            datosSalida.writeUTF(infoRutina);
        } catch (IOException e) {
            System.out.println("Error en el envio de la rutina de desconexion");
            throw new RuntimeException(e);
        }
        try {
            cantLedsFuncionando = datosEntrada.readUTF();
        } catch (IOException e) {
            System.out.println("Error en el recaudo de los leds funcionando");
            throw new RuntimeException(e);
        }
    }

    private void enviarRutinaDano() {
        String cantLedsFuncionando;
        try {
            datosSalida.writeUTF("1:10101010-2:10101010");
        } catch (IOException e) {
            System.out.println("Error en el envio de la rutina de desconexion");
            throw new RuntimeException(e);
        }

        try {
            cantLedsFuncionando = datosEntrada.readUTF();
        } catch (IOException e) {
            System.out.println("Error en el recaudo de los leds funcionando");
            throw new RuntimeException(e);
        }
    }

    public String buscarCodigo(Iterator iterator, String number) {
        int i = 0;
        while (iterator.hasNext()) {
            String codigo = iterator.next().toString();
            String[] codigoArr = codigo.split(":");
            if (number.equals(codigoArr[0])) {
                return codigo;
            }
        }
        return "";
    }

    public void transmitirConfiguracionInicial() {

        String cantLeds;
        try {
            datosSalida.writeUTF(leerConfiguracion());
            cantLeds = datosEntrada.readUTF();
        } catch (IOException e) {
            System.out.println("Error en la transmision de la configuracion inicial");
            throw new RuntimeException(e);
        }

        String[] leds = cantLeds.split("-");
        int[] ledsInt = null;
        ArrayList<int[]> cardLeds = new ArrayList<>();
        for (int i = 0, j = 0; i < leds.length; i++, j++) {
            if (i % 3 == 0) {
                if (i != 0) {
                    cardLeds.add(ledsInt);
                    if (cardLeds.size() == 2) {
                        allCardsLeds.add(cardLeds);
                        cardLeds = new ArrayList<>();
                    }
                }
                ledsInt = new int[3];
                j = 0;
            }
            ledsInt[j] = Integer.parseInt(leds[i]);
        }
    }
    private void delay(int mls) {
        try {
            Thread.sleep(mls);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void desconectar() {
        try {
            datosSalida.close();
            datosEntrada.close();
            System.out.println("Flujos terminados");
        } catch (IOException e) {
            System.out.println("Error en el cierre de flujos");
            throw new RuntimeException(e);
        }
    }

    public DataOutputStream getDatosSalida(){
        return datosSalida;
    }

    @Override
    public void run() {
        try {
            // Capturo el flujo de salida y lo asocio al dato de salida
            datosEntrada = new DataInputStream(host.getInputStream());
            datosSalida = new DataOutputStream(host.getOutputStream());

            transmitirConfiguracionInicial();
            int mls = 1000;
            int seg = 0;
            // Rutina de conexion
            Long tiempoRutina = readJSON.getTiempoRutinaConexion();
            for (int i = 0; i < tiempoRutina; i++) {
                System.err.println("Seg: " + seg);
                if (dano) {
                    enviarRutinaDano();
                } else {
                    enviarRutinaConexion(Integer.toString(i));
                }
                delay(mls);
                seg++;
                interseccionEstado.setTiempo(String.valueOf(seg), idInterseccion);
            }

            // Rutina normal
            tiempoRutina = readJSON.getTiempoRutina();
            while (true) {
                for (int i = 0; i < tiempoRutina; i++) {
                    System.err.println("Seg: " + seg);
                    if (dano) {
                        enviarRutinaDano();
                    } else {
                        enviarRutinaNormal(Integer.toString(i));
                    }
                    delay(mls);
                    seg++;
                    interseccionEstado.setTiempo(String.valueOf(seg), idInterseccion);
                }
            }

            // Rutina de desconexion
            /*tiempoRutina = readJSON.getTiempoRutinaDesconexion();
            for (int i = 0; i < tiempoRutina; i++) {
                enviarRutinaDesconexion(Integer.toString(i));
                delay(mls);
            }*/
        } catch (IOException ex) {
        } finally {
            desconectar();
        }
    }
}
