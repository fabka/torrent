/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.io.Serializable;

/**
 *
 * @author Fabi
 */
public class Archivo implements Serializable{
    private String nombre;
    private String hash;
    private double peso;
    
    Archivo(){
    }
    
    Archivo(String hash){
        this.hash = hash;
    }
    
    Archivo(String hash, double peso){
        this.hash = hash;
        this.peso = peso;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
