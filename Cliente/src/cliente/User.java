/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import static com.sun.org.apache.regexp.internal.RETest.test;
import coordinador.RMI;
import coordinador.RMIServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaccion.Operacion;

/**
 *
 * @author redes
 */
public class User extends Thread {

    private String ipS = "169.254.187.48";
    public int port = 6898;
    public int idTr;
    

//------------------------------------------------------------------------------
 
//------------------------------------------------------------------------------

    public static void opciones() {
        System.out.println("Selecione una opcion ");
        System.out.println("1. Mirar una lista con todos los archivos");
        System.out.println("2. Descargar archivo");
        System.out.println("3. Modificar archivos");
        System.out.println("4. Salir");
    }

//------------------------------------------------------------------------------
    public static void menu() {
        System.out.println("Selecione una opcion ");
        System.out.println("1. Agregar archivo ");
        System.out.println("2. Crear transaccion");
        System.out.println("3. Salir");
    }
//------------------------------------------------------------------------------

    public static int iniciarSesion() {

        System.out.println("1. Agregar archivo ");
        System.out.println("2. Crear transaccion");
        return 10;
    }
//----------------------------------------------------------------------------

    public int validar(int idTr) {
        try {

            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            int idT = rmi.validacion(idTr);
            return idT;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }
//----------------------------------------------------------------------------

    public int openTransaccion2(String idUsuario, String s) {
        try {

            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            int idT = rmi.openTransaction(idUsuario, s);
            return idT;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }
//------------------------------------------------------------------------------

    public void registrarse(String idUsuario, String password) {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            if (!rmi.iniciarSesion(idUsuario, password)) {
                rmi.registrarse(idUsuario, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------

    public void usuarios() {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            //System.out.println("antes usuarios ");
            rmi.usuarios();
            //System.out.println("antes usuarios ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------

    public ArrayList<Archivo> lista_archivos() {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");

            return rmi.archivosDisponibles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    //------------------------------------------------------------------------------
    public void verDirectorio() throws NotBoundException {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            rmi.verDirectorio();
        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //------------------------------------------------------------------------------
    public void agregar(String nombre, String hash, double peso, String ip, int puerto) throws NotBoundException {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            rmi.agregar(nombre, hash, peso, ip, puerto);
        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public byte[] descargar(String nombre, String hash, String ipC) throws NotBoundException {
        
        try {
            Registry reg = LocateRegistry.getRegistry(ipC.replace("/", ""), 6898);

            IServer rmi = (IServer) reg.lookup("Servidor");

            byte[] bytes = rmi.descargar(hash, nombre,ipC);

            return bytes;

        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//------------------------------------------------------------------------------

    public ArrayList<Zocalo> pedir_usuarios(String nombre) throws NotBoundException {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            System.out.println("estoy aqui ");
            return rmi.clientesDisponibles(nombre);
        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
//------------------------------------------------------------------------------
 public ArrayList<Zocalo> pedir_usuariosT(String nombre) throws NotBoundException {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            System.out.println("estoy aqui ");
            return rmi.clientesDisponibles(nombre);
        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
//------------------------------------------------------------------------------

    public boolean verificarConexion(InetAddress ip) {
        boolean conectado = false;
        try {
            // Ip de la máquina remota
            System.out.println("ip " + ip);
            if (ip.isReachable(5000)) {
                conectado = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conectado;
    }
    //------------------------------------------------------------------------------   

    public Archivo obtenerArchivo(String nombre) throws NotBoundException {
        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            return rmi.obtenerArchivo(nombre);
        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //------------------------------------------------------------------------------   
    public void modificar(String archivo, String texto, int index, String ipC) throws NotBoundException {

        try {
            Registry reg = LocateRegistry.getRegistry(ipC.replace("/", ""), 6898);

            IServer rmi = (IServer) reg.lookup("Servidor");

             rmi.anadirTexto(archivo, texto, index);

        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//------------------------------------------------------------------------------

    public void addResource(int idTr, String hash, Boolean operacion) throws NotBoundException {

        try {
            Registry reg = LocateRegistry.getRegistry(ipS, 6897);

            RMI rmi = (RMI) reg.lookup("Directorio");

            rmi.addResource(idTr, hash, operacion);

        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificar(User test, String path, String text, int index, String ipC){
        Modificar m = new Modificar(test, path, text, index, ipC);
    }
//------------------------------------------------------------------------------
    public class Modificar implements Runnable{
        User test;
        String path;
        String texto;
        int index;
        String ipC;
        
        public Modificar (User u, String path, String texto, int index, String ipC){
            test = u;
            this.path = path;
            this.texto = texto;
            this.index = index;
            this.ipC = ipC;
        }
        
        @Override
        public void run() {
            try {
                test.modificar(path, texto, index, ipC);
            } catch (NotBoundException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public class Descargar implements Runnable{
        User test;
        String path;
        String texto;
        String hash;
        String ipC;
        String nombre;
        
        public Descargar(String nombre, String hash, String ipC){
            this.nombre = nombre;
            this.hash = hash;
            this.ipC = ipC;
        }

        @Override
        public void run() {
            FileOutputStream fos = null;
            try {
                byte[] bytes = test.descargar(nombre, hash, ipC);
                File ofile = new File(nombre);
                fos = new FileOutputStream("Archivos/" + ofile);
                fos.write(bytes);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
