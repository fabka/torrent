/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

/**
 *
 * @author Fabi
 */
public class Archivo {
    private String hash;
    private int peso;
    
    Archivo(){
    }
    
    Archivo(String hash){
        this.hash = hash;
    }
    
    Archivo(String hash, int peso){
        this.hash = hash;
        this.peso = peso;
    }
    
    public String getHash() {
        return hash;
    }

    public int getPeso() {
        return peso;
    }
}
