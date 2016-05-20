/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import transaccion.Transaccion;

/**
 *
 * @author Fabi y Jime
 */
public interface IServer extends Remote {
        
  public byte[] enviar(String hash, String nombre) throws RemoteException ;
 public boolean anadirTexto(String archivo, String texto, int index)  throws RemoteException ;
    
    
}

