/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

//import Manejador.TCPClient;
import static cliente.User.menu;
import static cliente.User.opciones;
import coordinador.RMI;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author redes
 */
public class Client  {
 private  int serverPort = 7895 ;
  private  int serverPortS = 7896 ;
 private String host = "localhost";
 public String idUsuario ;
 public String password;

 private String ipS = "169.254.187.48";

private ArrayList<Modificados > modificados;


//------------------------------------------------------------------------------
    private void Descargar() {
        try {
            Registry reg = LocateRegistry.getRegistry(host,1099);
            RMI rmi = (RMI)reg.lookup("server");
            System.out.println("Conecting to server...");
         //   int test = rmi.descargar("nombre");
           // System.out.println(test+"");
        } catch (Exception e) {
            
        }
    } 

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
                 //   System.out.println("ip " + InetAddress.getLocalHost().getHostAddress());
                    Archivo a = new Archivo(nombre, tam, path);
                    test.agregar(nombre, a.getHash(), tam, InetAddress.getLocalHost().getHostAddress(), port);
                    //cliente.agregarArchivo(tam, nombre, ip,port, path);
                } else {
                    test.idTr = test.openTransaccion2(cliente.idUsuario, cliente.password);

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
                            if (usuarios.isEmpty()) {
                                System.out.println("Ningun servidor tiene el archivo pedido");
                            } else {

                                test.verDirectorio();
                                String ipC = null;
                                for (Zocalo z : usuarios) {
                                    if (test.verificarConexion(z.getIp())) {
                                        ipC = z.getIp().toString();
                                    }
                                }

                                Archivo archivo = test.obtenerArchivo(nombre);
                                //True es descargar
                                test.addResource(test.idTr, archivo.getHash(), Boolean.TRUE);

                                byte[] bytes = test.descargar(nombre, archivo.getHash(), ipC);
                                File ofile = new File(nombre);
                                FileOutputStream fos = new FileOutputStream("Archivos/" + ofile);
                                fos.write(bytes);
                            }
                            break;
                        case 3:
                            System.out.println("Digite el nombre del archivo");
                            nombre = br.readLine();
                            System.out.println("Digite la modificación que desea realizar");
                            String modif = br.readLine();

                            usuarios = test.pedir_usuarios(nombre);
                            if (usuarios.isEmpty()) {
                                System.out.println("Ningun servidor tiene el archivo pedido");
                            }else{
                                test.verDirectorio();
                                String ipC = null;
                                for (Zocalo z : usuarios) {
                                    if (test.verificarConexion(z.getIp())) {
                                        ipC = z.getIp().toString();
                                    }
                                }
                                Path path2;
                                path2 = Paths.get("Archivos/" + nombre);
                                
                                Archivo archivo = test. obtenerArchivo(nombre);
                                 test.modificar(test, path2.toString(), modif, -1, ipC);
                                archivo = test. obtenerArchivo(nombre);
                                Modificados m = new Modificados();
                                m.hash_ant = archivo.getHash();
                                m.hash_nuevo = 
                                m.ip =ipC;
                                
                                test.addResource(test.idTr, archivo.getHash(), Boolean.TRUE);
                            }
                            break;
                    }
                    opciones();
                    opcion = Integer.parseInt(br.readLine());
                } while (opcion < 4 && opcion > 0);
                if (opcion == 4) {
                    System.out.println("validar la transaccion ");
                }
            } while (menu != 3);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//-------------------------------------------------------------------------------------------------------
public  void iniciarSesion()
{
      System.out.println("Ingrese su cedula ");
      System.out.println("1. Agregar archivo ");
      System.out.println("2. Crear transaccion");
}
}




