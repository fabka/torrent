/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import cliente.Archivo;
import cliente.Zocalo;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import transaccion.Transaccion;

/**
 *
 * @author jimena
 */
public interface RMI extends Remote {
        
    public int openTransaction(String idUsuario, String password) throws RemoteException;
    public Boolean closeTransaction(Long tId) throws RemoteException;
    public Boolean abortTransaction(Transaccion transaccion) throws RemoteException;
    public Boolean addResource(int idTr, String hash, Boolean operacion)throws RemoteException;
    public int descargar( String archivo, int id) throws RemoteException;
    public int modificar(String idUsuario, String tipo, String archivo, int iId) throws RemoteException; 
    public Boolean iniciarSesion(String idUsuario, String contrasenia) throws RemoteException;
    public List<Archivo> listaArchivos()throws RemoteException;  
    public Boolean registrarse(String idUsuario, String contrasenia)throws RemoteException;
    public void usuarios() throws RemoteException;
    public void agregar(String nombre, String path, double peso, String ip, int puerto) throws RemoteException;
    public ArrayList<Archivo> archivosDisponibles() throws RemoteException;
    public ArrayList<Zocalo> clientesDisponibles(String nombre) throws RemoteException;
    public void verDirectorio() throws RemoteException;
    public Archivo obtenerArchivo(String nombre) throws RemoteException;
    public int validacion(int idTransaccion) throws RemoteException;
    public ArrayList<Zocalo> usuariosActivos() throws RemoteException;
}


