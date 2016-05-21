/*
 * Servidor del lado cliente
 */
/**
 *
 * @author redes
 */
package cliente;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends UnicastRemoteObject implements IServer {

    List<Archivo> archivos;
    private String host = "localhost";
    private int serverPort = 7895;

    public Server() throws RemoteException {
        super();

    }
//-----------------------------------------------------------------------------
     @Override
      public byte[] descargar(String hash, String nombre, String ipC) throws RemoteException
      {
         try {
            Registry reg = LocateRegistry.getRegistry(ipC, 6898);

            IServer rmi = (IServer) reg.lookup("Servidor");

            byte[] bytes = rmi.enviar(hash, nombre);

            return bytes;

        } catch (RemoteException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
          
      }
//------------------------------------------------------------------------------
    @Override
    public byte[] enviar(String hash, String nombre) throws RemoteException {
        try {
         
            FileInputStream fis = null;
            Archivo a = new Archivo();
            System.out.println(" entre a la funcion 22222");
            System.out.println("arhcivo " + nombre);
            File carpeta = new File("Archivos");
            System.out.println(" entre a la funcion 33333");
            File[] archivos = carpeta.listFiles();
            File temp;
            Path path = null ;
            System.out.println(" entre a la funcion 4444");
            System.out.println(" el size de los archivos "+archivos.length);
            for (File e : archivos) {
                System.out.println("archivos "+e.getName());
                if (a.md5("Archivos/" + e.getName()).equals(hash)) {
                    temp = e;
                    path = Paths.get("Archivos/" + e.getName());
                    System.out.println("estamos aqui ");
                }
            }
            byte[] data = Files.readAllBytes(path);
                 System.out.println("datos -..."+data);
            return data;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//------------------------------------------------------------------------------
        @Override
     public void anadirTexto(String archivo, String texto, int index) throws RemoteException {
        String str = obtenerArchivo(archivo);
        if(index < 0)
            index = str.length()-1;
        str = new StringBuilder(str).insert(index, texto).toString();
        
         escribirArchivo(archivo, str);
    }
//------------------------------------------------------------------------------
    private static String obtenerArchivo(String archivo) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(archivo));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }   
            return sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
//------------------------------------------------------------------------------
    private static void escribirArchivo(String archivo, String str) {
        try {
            File ofile = new File(archivo+".");
            Path path = Paths.get("Archivos/" + archivo);
            byte[] data = Files.readAllBytes(path);
            FileOutputStream fos = new FileOutputStream("Archivos/" + ofile);
            fos.write(data);
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter( new File(archivo+".")));
                writer.write(str);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                
            } finally {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      //Archivo.md5("Archivos/" + archivo+".");
    }
//------------------------------------------------------------------------------

}
