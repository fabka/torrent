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
import java.rmi.RemoteException;
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

//------------------------------------------------------------------------------
    @Override
    public byte[] enviar(String hash, String nombre) throws RemoteException {
        try {
            FileInputStream fis = null;
            Archivo a = new Archivo();
            System.out.println("arhcivo " + nombre);
            File carpeta = new File("Archivos");
            File[] archivos = carpeta.listFiles();
            File temp;
            Path path = null ;
            for (File e : archivos) {
                if (a.md5("Archivos/" + e.getName()).equals(nombre)) {
                    temp = e;
                    path = Paths.get("Archivos/" + e.getName());
                }
            }
            byte[] data = Files.readAllBytes(path);
            
            return data;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//------------------------------------------------------------------------------
        @Override
     public boolean anadirTexto(String archivo, String texto, int index) throws RemoteException {
        String str = obtenerArchivo(archivo);
        if(index < 0)
            index = str.length()-1;
        str = new StringBuilder(str).insert(index, texto).toString();
        return escribirArchivo(archivo, str);
    }
//------------------------------------------------------------------------------
    private static String obtenerArchivo(String archivo) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("test.txt"));
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
    private static boolean escribirArchivo(String archivo, String str) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter( new File(archivo)));
            writer.write(str);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
//------------------------------------------------------------------------------

}
