/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txttest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Monica
 */
public class Main {
    
    public static final int FINAL = -1;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        anadirTexto("test.txt", "Inserte texto", FINAL);
    }   

    private static boolean anadirTexto(String archivo, String texto, int index) {
        String str = obtenerArchivo(archivo);
        if(index < 0)
            index = str.length()-1;
        str = new StringBuilder(str).insert(index, texto).toString();
        return escribirArchivo(archivo, str);
    }

    private static String obtenerArchivo(String archivo) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("test.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }   
            return sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private static boolean escribirArchivo(String archivo, String str) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter( new File(archivo)));
            writer.write(str);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
}
