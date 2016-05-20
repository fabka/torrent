/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

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

/**
 *
 * @author redes
 */
public class User {

    private String ipS = "169.254.187.48";
    private int port = 6898;

//------------------------------------------------------------------------------
    public static void main(String arg[]) throws NotBoundException {
        int port = 6898;
        User test = new User();

        try {
            // Server servidor = new Server();
            Client cliente = new Client();
            cliente.iniciarSesion();

            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            int opcion = 0;
            double tam;
            String nombre, path, cedula, password;
            System.out.println("Ingrese su nombre de usuario ");
            cliente.idUsuario = br.readLine();
            System.out.println("Ingrese su contraseña ");
            password = br.readLine();
            cliente.password = org.apache.commons.codec.digest.DigestUtils.md5Hex(password);
            test.registrarse(cliente.idUsuario, cliente.password);
            test.usuarios();
            ArrayList<Zocalo> usuarios;
            int menu;
            do {
                menu();
                opcion = 0;
                menu = Integer.parseInt(br.readLine());
                if (menu == 1) {
                    System.out.println("Digite nombre del archivo");
                    nombre = br.readLine();
                    path = "Archivos/" + nombre;
                    File f = new File(path);
                    if (f.exists()) {
                        tam = f.length();
                    } else {
                        tam = -1;
                    }
                    System.out.println("ip " +InetAddress.getLocalHost().getHostAddress());
                    Archivo a = new Archivo(nombre, tam, path);
                    test.agregar(nombre, a.getHash(), tam, InetAddress.getLocalHost().getHostAddress(), port);
                    //cliente.agregarArchivo(tam, nombre, ip,port, path);
                } else {
                    test.openTransaccion2(cliente.idUsuario, cliente.password);
              
                    opciones();
                    opcion = Integer.parseInt(br.readLine());
                }

                do {
                    System.out.println("Seleccione que operacion desea para la transaccion ");
                    switch (opcion) {
                        case 1:
                            ArrayList<Archivo> archivos = new ArrayList<>();
                            archivos = test.lista_archivos();
                            if (archivos.size() == 0) {
                                System.out.println("El servidor no contiene archivos en este momento");
                            }
                            for (Archivo a : archivos) {
                                System.out.println(a.getNombre());
                            }
                            break;

                        case 2:
                            System.out.println("Digite el nombre del archivo");
                            nombre = br.readLine();
                            usuarios = test.pedir_usuarios(nombre);
                            if (usuarios.isEmpty())
                            System.out.println("Ningun servidor tiene el archivo pedido");
                            else
                            {
                          
                                test.verDirectorio();
                                String ipC=null;
                                for(Zocalo z : usuarios) 
                                {
                                  if(test.verificarConexion(z.getIp()))
                                    {
                                        ipC = z.getIp().toString();
                                    }
                                 }
                                Archivo archivo = test.obtenerArchivo(nombre);
                                
                  byte[] bytes = test.descargar(nombre,archivo.getHash(),ipC);
                        File ofile = new File (nombre);
                                 FileOutputStream fos = new FileOutputStream("Archivos/"+ofile);
                                 fos.write(bytes);
                            }
                            break;
                        case 3:
                            System.out.println("Digite el nombre del archivo");
                            nombre = br.readLine();
                            System.out.println("Digite la modificación que desea realizar");
                            String modif = br.readLine();
                           
                            usuarios = test.pedir_usuarios(nombre);
                            if (usuarios.isEmpty())
                            System.out.println("Ningun servidor tiene el archivo pedido");
                            else
                            {
                          
                                test.verDirectorio();
                                String ipC=null;
                                for(Zocalo z : usuarios) 
                                {
                                  if(test.verificarConexion(z.getIp()))
                                    {
                                        ipC = z.getIp().toString();
                                    }
                                 }
                                 Path path2;
                                 path2 = Paths.get("Archivos/" + nombre);
                                test.modificar(path2.toString(), modif, -1, ipC);
                            }

                            break;
                    }
                    opciones();
                    opcion = Integer.parseInt(br.readLine());
                } while (opcion < 4 && opcion > 0);
            } while (menu != 3);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
    public void openTransaccion2(String idUsuario, String s) {
        try {

            Registry reg = LocateRegistry.getRegistry(ipS, 6897);
            RMI rmi = (RMI) reg.lookup("Directorio");
            int idT = rmi.openTransaction(idUsuario, s);
            System.out.println("id de la transaccion " + idT);
        } catch (Exception e) {
            e.printStackTrace();

        }
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
            System.out.println("antes usuarios ");
            rmi.usuarios();
            System.out.println("antes usuarios ");
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
          
            byte[] bytes= rmi.enviar(hash, nombre);
         
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
public boolean verificarConexion(InetAddress ip)
{
    boolean conectado = false;
     try {
         // Ip de la máquina remota
         System.out.println("ip "+ip);
         if (ip.isReachable(5000))
             conectado= true;
     } catch (IOException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
     }
     return conectado;

}
 //------------------------------------------------------------------------------   

    private Archivo obtenerArchivo(String nombre) throws NotBoundException {
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
   public Boolean modificar(String archivo, String texto, int index, String ipC) throws NotBoundException {
        
        try {
            Registry reg = LocateRegistry.getRegistry(ipC.replace("/", ""), 6898);
       
            IServer rmi = (IServer) reg.lookup("Servidor");
          
            return  rmi.anadirTexto(archivo,texto, index);
            
        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
//------------------------------------------------------------------------------
}