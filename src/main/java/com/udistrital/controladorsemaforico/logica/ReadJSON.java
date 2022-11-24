package com.udistrital.controladorsemaforico.logica;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadJSON {
    private ArrayList confsConexion = new ArrayList<JSONObject>();
    private ArrayList confsDesconexion = new ArrayList<JSONObject>();
    private ArrayList confsPlan = new ArrayList<JSONObject>();

    private JSONObject jsonObject;

    public void readFile(String path){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(path));
            jsonObject = (JSONObject)obj;
            JSONArray confs = (JSONArray)jsonObject.get("CONF");
            Iterator iterator = confs.iterator();
            while (iterator.hasNext()) {
                String aux = iterator.next().toString();
                if (aux.indexOf('C') != -1)
                    confsConexion.add(jsonObject.get(aux));
                else if (aux.indexOf('D') != -1)
                    confsDesconexion.add(jsonObject.get(aux));
                else
                    confsPlan.add(jsonObject.get(aux));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    public String getPrimerMensaje(){
        Iterator iterator = confsConexion.iterator();
        String numbers = "";
        while(iterator.hasNext()){
            JSONObject aux = (JSONObject) iterator.next();
            String t2 = (String)aux.get("t2");
            numbers += t2+"-";

        }
        numbers = numbers.substring(0, numbers.length()-1);
        return numbers;
    }

    public Long getTiempoRutinaDesconexion() {
        Iterator iterator = confsDesconexion.iterator();
        String numbers = "";
        JSONObject aux = (JSONObject) iterator.next();
        Long tc = (Long) aux.get("TC");
        return tc;
    }

    public Long getTiempoRutinaConexion(){
        Iterator iterator = confsConexion.iterator();
        String numbers = "";
        JSONObject aux = (JSONObject) iterator.next();
        Long tc = (Long) aux.get("TC");
        return tc;
    }

    public Long getTiempoRutina(){
        Iterator iterator = confsPlan.iterator();
        String numbers = "";
        JSONObject aux = (JSONObject) iterator.next();
        Long tc = (Long) aux.get("TC");
        return tc;
    }

    public ArrayList<JSONArray> getRutina(ArrayList<JSONObject> confs){
        Iterator iterator = confs.iterator();
        ArrayList<JSONArray> arr = new ArrayList<JSONArray>();
        while (iterator.hasNext()){
            JSONObject aux = (JSONObject) iterator.next();
            JSONArray tiempos = (JSONArray) aux.get("tiempos");
            arr.add(tiempos);
        }
        //System.out.println(arr);
        return arr;
    }

    public ArrayList<JSONArray> getRutinaConexion(){
        return getRutina(confsConexion);

    }

    public ArrayList<JSONArray> getRutinaPlan(){
        return getRutina(confsPlan);
    }

    public ArrayList<JSONArray> getRutinaDesconexion(){
        return getRutina(confsDesconexion);
    }

}
