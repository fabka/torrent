/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmitest;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Monica
 */
public interface RMI extends Remote {
    
    public void agregar(String nombre, String hash, double peso, String ip, int puerto);
    
    public ArrayList<Archivo> archivosDisponibles();
    
    public void obtenerArchivo(String nombre); //Pendiente de tipo de dato del retorno.
    
    public void verDirectorio(); //Pendiente de tipo de dato del retorno.
   
    public boolean agregarUsuario( String username, String password );
    
    public boolean iniciarSesion( String username, String password );
    
    public boolean cerrarSesion( String username );
}
