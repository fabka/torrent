/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.Serializable;

/**
 *
 * @author redes
 */
public class Archivo implements Serializable {
    private String nombre;
    private String hash;
    private float peso;
    
    Archivo(){
    }
    
    Archivo(String nombre, float peso){
        this.nombre = nombre;
        this.peso = peso;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHash() {
        return hash;
    }

    public float getPeso() {
        return peso;
    }
}
