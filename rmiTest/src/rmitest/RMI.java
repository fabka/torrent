/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmitest;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Monica
 */
public interface RMI extends Remote {
        
    public void actualizarArchivo(String nombre, int fileLength, byte[] bytedFile) throws RemoteException;
}
