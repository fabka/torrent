/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Directory;

import cliente.Archivo;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import transaccion.Transaccion;

/**
 *
 * @author jimena
 */
public interface SServer extends Remote{
    public Boolean iniciarSesion(String idUsuario, String contrasenia) throws RemoteException;
    public List<Archivo> listaArchivos()throws RemoteException;  
    public Boolean devolverTransaccion (String idUsuario, Transaccion tID) throws RemoteException;
    
}
