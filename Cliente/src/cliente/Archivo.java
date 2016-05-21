/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author redes
 */
public class Archivo implements Serializable {

    private String nombre;
    private String hash;
    private double peso;

    public Archivo() {
    }

    public Archivo(String nombre, double peso, String path) {
        this.nombre = nombre;
        this.peso = peso;
        this.hash = md5(path);
    }

    public Archivo(String nombre, String hash, double peso) {
        this.nombre = nombre;
        this.peso = peso;
        this.hash = hash;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHash() {
        return hash;
    }

    public String setHash() {
        return hash;
    }

    public double getPeso() {
        return peso;
    }

    public static String md5(String ruta) {

        File f = new File(ruta);
        FileInputStream fis;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fis = new FileInputStream(new File(ruta));
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
            fis.close();
            return md5;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
