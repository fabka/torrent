/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import Directory.Directorio;
import Directory.SServer;
import cliente.Archivo;
import cliente.Zocalo;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.Operacion;
import transaccion.Transaccion;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author jimena
 */
public class RMIServer extends UnicastRemoteObject implements RMI {

    private int contTr;
    private Registry registry;
    private PriorityQueue<Transaccion> transacciones_cp;
    private HashMap<Integer,Transaccion> transacciones_mapa;
    private HashMap<Operacion, Long> op_validas;
    private Usuarios usuarios;
    private Directorio directorio;

    public RMIServer() throws RemoteException {
        super();
        usuarios = new Usuarios();
        directorio = new Directorio();
        transacciones_cp = new PriorityQueue<>();
        transacciones_mapa = new HashMap<>();
        op_validas = new HashMap<>();
    }

    public Transaccion newTransaccion() {
        contTr++;
        Transaccion transaction = new Transaccion(contTr);
        transacciones_cp.add(transaction);
        
        transacciones_mapa.put(contTr, transaction);
        return transaction;
    }

    @Override
    public int openTransaction(String idUsuario, String password) throws RemoteException {
        Boolean valid = false;
        valid = this.iniciarSesion(idUsuario, password);
        if (!valid) {
            return -1;
        }
        Transaccion tr = newTransaccion();
        tr.setIdUsr(idUsuario);
        return tr.getId();
    }

    @Override
    public Boolean closeTransaction(Long tId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean abortTransaction(Transaccion transaccion) throws RemoteException, AccessException {
        System.out.println("Se pasa a abortar la transaccion");
        Transaccion closed = transaccion;
        if (closed == null) {
            throw new RemoteException();
        }

        SServer directorio = null;
        try {
            directorio = (SServer) registry.lookup("Directorio");
        } catch (NotBoundException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        directorio.devolverTransaccion(closed.getIdUsr(), closed);

        // transacciones.remove(idTr);
        return true;
    }

    @Override
    public Boolean addResource(int idTr, String hash, Boolean operacion) throws RemoteException {
         Transaccion transaccion = transacciones_mapa.get(idTr);
         Operacion accion = new Operacion(hash, operacion);
         transaccion.addOperacion(accion);
        return true;
    }

    @Override
    public int descargar(String archivo, int id) throws RemoteException {
        Transaccion t = new Transaccion(id);
        if (t == null) {
            throw new RemoteException();
        }
        return 0;
    }

    @Override
    public int modificar(String idUsuario, String tipo, String archivo, int iId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int validacion(int idTransaccion) throws RemoteException{
        Transaccion transaccion = transacciones_mapa.get(idTransaccion);
        int valida = 1;
        Transaccion ultima = transacciones_cp.peek();
        if (ultima.getId() == transaccion.getId()) {
            ArrayList<Operacion> op = transaccion.getAcciones();
            for (Operacion p : op) {
                Set<Operacion> keySet = op_validas.keySet();
                Operacion cp = new Operacion(p.getArchivo(),!p.isOperacion());
               
                if( keySet.contains(p) && p.isOperacion() && op_validas.get(p)>transaccion.getStartTime())
                    valida = 0;
                else if(keySet.contains(cp) && p.isOperacion()&& op_validas.get(cp)>transaccion.getStartTime())
                    valida = 0;
                else if(!p.isOperacion() && keySet.contains(cp)&& op_validas.get(cp)>transaccion.getStartTime())
                    valida = 0;
                else{
                    if(op_validas.equals(p))
                        op_validas.remove(p);
                    else if(op_validas.equals(cp))
                        op_validas.remove(cp);
                }
            }
        }
        else 
            valida = 3;
        if(valida==1)
            cambiarmapa(transaccion);
        return valida;
    }
    public void  cambiarmapa(Transaccion transaccion)
    {
         Long endTime = System.currentTimeMillis();
               ArrayList<Operacion> op = transaccion.getAcciones();
            for (Operacion p : op) {
                Operacion cp = new Operacion(p.getArchivo(),!p.isOperacion());
                 if(op_validas.equals(p))
                 {
                    op_validas.remove(p);
                    op_validas.put(p, endTime);
                 }
                else if(op_validas.equals(cp))
                    {
                     op_validas.remove(cp);
                    op_validas.put(cp, endTime);
                    }
            }
    }

    @Override
    public Boolean iniciarSesion(String idUsuario, String contrasenia) throws RemoteException {
        return usuarios.iniciarSesion(idUsuario, contrasenia);
    }

    @Override
    public Boolean registrarse(String idUsuario, String contrasenia) throws RemoteException {
        return usuarios.agregarUsuario(idUsuario, contrasenia);
    }

    @Override
    public void usuarios() throws RemoteException {
        usuarios.usuarios();
    }

    @Override
    public List<Archivo> listaArchivos() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void agregar(String nombre, String hash, double peso, String ip, int puerto) throws RemoteException{
        System.out.println("nombre:"+nombre+", hash:"+hash+", peso:"+", ip:"+ip+", puerto:"+puerto);
        directorio.anadirZocalo(nombre, hash, peso, ip, puerto+"");
        directorio.save();
        System.out.println("Se anadio el archivo "+nombre+" correctamente");
        verDirectorio();
    }

    @Override
    public ArrayList<Archivo> archivosDisponibles() throws RemoteException{
        return directorio.obtenerArchivos();
    }

    @Override
    public ArrayList<Zocalo> clientesDisponibles(String nombre) throws RemoteException{
        Archivo a = directorio.obtenerArchivo(nombre);
        return directorio.obtenerListaZocalos(a.getHash());
    }
    
    @Override
    public void verDirectorio() throws RemoteException {
        ArrayList<Archivo> archivos = directorio.obtenerArchivos();
        System.out.println("Tam archivos = " + archivos.size());
        for (Archivo archivo : archivos) {
            System.out.println("Nombre: " + archivo.getNombre());
            System.out.println("hash  : " + archivo.getHash());
            System.out.println("peso  : " + archivo.getPeso());
            ArrayList<Zocalo> zocalos = directorio.obtenerListaZocalos(archivo.getHash());
            for (Zocalo z : zocalos) {
                System.out.println("\t" + z.getIp() + ":" + z.getPuerto());
            }
        }
    }
    @Override
    public ArrayList<Zocalo> usuariosActivos() throws RemoteException{
         return usuarios.obtenerActivos();
    }
    @Override
    public Archivo obtenerArchivo(String nombre) throws RemoteException{
        return directorio.obtenerArchivo(nombre);
    }
}
