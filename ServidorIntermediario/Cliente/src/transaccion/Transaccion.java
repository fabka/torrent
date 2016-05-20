/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author jimena
 */
public class Transaccion implements Comparable {
    private int id;
  //  private Set<String> recursosAfectados;
    private String idUser;
    private Long startTime;
    private ArrayList<Operacion> acciones;
    

    public ArrayList<Operacion> getAcciones() {
        return acciones;
    }

    public void setAcciones(ArrayList<Operacion> acciones) {
        this.acciones = acciones;
    }
    

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    

    public String getIdUsr() {
        return idUser;
    }

    public void setIdUsr(String idUser) {
        this.idUser = idUser;
    }


    public int getId() {
        return id;
    }

    public void settId(int id) {
        this.id = id;
    }

    public Transaccion(int id) {
        this.id = id;
    //    recursosAfectados = new HashSet<>();
        startTime = System.currentTimeMillis();
        acciones = new ArrayList<>();
    }
    
    public Boolean antes(Transaccion transaccion){
        return startTime <= transaccion.getStartTime();
    }
    
    public Transaccion(){
      //  recursosAfectados = new HashSet<>();
        startTime = System.currentTimeMillis();
        acciones = new ArrayList<>();
    }

    @Override
    public int compareTo(Object o) {
        Transaccion t = (Transaccion)o;
       return Long.compare(this.startTime,t.getStartTime());
    }
}