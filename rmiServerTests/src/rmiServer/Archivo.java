package rmiServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Fabi
 */
public class Archivo implements Serializable{
    private String hash;
    private String nombre;
    private double peso;
   
    
    Archivo(){
    }
    
    int numeroPartes(){
        int partes = ((int)(peso/2048))+1;
        if (partes > 50)
            return 50;
        return partes;
    }
    
    Archivo(String hash){
        this.hash = hash;
    }
    
    Archivo(String nombre, String hash, double peso){
        this.nombre = nombre;
        this.hash = hash;
        this.peso = peso;
    }
     
    public String getHash() {
        return hash;
    }

    
    public double getPeso() {
        return peso;
    }

    public String getNombre() {
        return nombre;
    }
    
    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Archivo ){
            Archivo a = (Archivo)obj;
            return this.hash.equals(a.getHash()) && a.getPeso() == peso 
                    && this.nombre.equals(a.getNombre());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.hash);
        hash = 41 * hash + Objects.hashCode(this.nombre);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.peso) ^ (Double.doubleToLongBits(this.peso) >>> 32));
        return hash;
    }
}
