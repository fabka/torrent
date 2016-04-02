/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author sala-a
 */
public class MainTest {
    /*public static void main (String args[]) {
        Directorio directorio = new Directorio();
        Scanner keyboard = new Scanner(System.in);
        String opcion="", nombre, hash, ip, puerto, peso;
        
        while( !opcion.equals("salir")){
            System.out.println("Ingrese comando");
            opcion = keyboard.nextLine();
            switch(opcion){
                case "agregar":
                    System.out.println("Ingrese nombre");
                    nombre = keyboard.nextLine();
                    System.out.println("Ingrese hash");
                    hash = keyboard.nextLine();
                    System.out.println("Ingrese peso");
                    peso = keyboard.nextLine();
                    System.out.println("Ingrese ip");
                    ip = keyboard.nextLine();
                    System.out.println("Ingrese puerto");
                    puerto = keyboard.nextLine();
                    directorio.anadirZocalo(nombre, hash, Double.parseDouble(peso), ip, puerto);
                    break;
                case "obtener todos":
                    for(Archivo a: directorio.obtenerArchivos()){
                        System.out.println("* "+a.getNombre());
                    }
                    break;
                case "obtener lista":
                    System.out.println("Ingrese nombre");
                    nombre = keyboard.nextLine();
                    Archivo a = directorio.obtenerArchivo(nombre);
                    ArrayList<Zocalo> zocalos = directorio.obtenerListaZocalos(a.getHash());
                    for(Zocalo z: zocalos)
                        System.out.println("ip="+z.getIp()+", puerto="+z.getPuerto());
                break;
                case "obtener nombre":
                    System.out.println("Ingrese nombre");
                    nombre = keyboard.nextLine();
                    a = directorio.obtenerArchivo(nombre);
                    System.out.println("hash="+a.getHash()+", peso="+a.getPeso());
                break;
            }
        }
        directorio.close();
    }*/
}
