package rmiServer;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import rmitest.RMI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Monica
 */
public class RMIServer extends UnicastRemoteObject implements RMI{
    
    Directorio directorio;
    
    public RMIServer() throws RemoteException {
        super();
    }    
    
    public static void main(String args[]){
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", new RMIServer());
            System.out.println("server start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void agregar(String nombre, String hash, double peso, String ip, int puerto) {
        directorio.anadirZocalo(nombre, hash, peso, ip, puerto);
        directorio.save();
        System.out.println("Se añadio el archivo "+nombre+" correctamente");
    }
    
    
    @Override
    public ArrayList<rmitest.Archivo> archivosDisponibles() {
        return (ArrayList<rmitest.Archivo>) directorio.obtenerArchivos();
    }

    @Override
    public void obtenerArchivo(String nombre) {
        ArrayList<Archivo> archivos = directorio.obtenerArchivos();
        Archivo a = directorio.obtenerArchivo(nombre);
        if (a != null){
            //out.writeUTF(a.getHash()); 
            Zocalo zocalo = new Zocalo(clientSocket.getInetAddress().toString().replace("/",""), 7895+"");
            ArrayList<Zocalo> zocalos = directorio.obtenerListaZocalos(a.getHash());
            int tam = zocalos.size();
            if (zocalos.contains(zocalo))
                tam--;        
            //out.writeUTF(tam+"");
            for(Zocalo z: zocalos){
                if(!z.equals(zocalo)){
                    //out.writeUTF(z.getIp());
                    //out.writeUTF(z.getPuerto());
                }
            }                            
        }else{
            //out.writeUTF("-1");
        }
    }

    @Override
    public void verDirectorio() {
        ArrayList<Archivo> archivos = directorio.obtenerArchivos();
        System.out.println("Tam archivos = "+archivos.size());
        for( Archivo archivo: archivos ){
            System.out.println("Nombre: "+archivo.getNombre());
            System.out.println("hash  : "+archivo.getHash());
            System.out.println("peso  : "+archivo.getPeso());
            ArrayList<Zocalo> zocalos = directorio.obtenerListaZocalos(archivo.getHash());
            for(Zocalo z: zocalos){
                System.out.println("\t"+z.getIp()+":"+z.getPuerto());
            }
        }
    }

    @Override
    public boolean agregarUsuario(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean iniciarSesion(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cerrarSesion(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
