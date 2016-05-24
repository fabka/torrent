
import com.sun.corba.se.spi.activation.Server;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void actualizarArchivo(String nombre, int fileLength, byte[] bytedFile) throws RemoteException {
        try {
            FileOutputStream fos = new FileOutputStream("archivos_cliente/"+nombre);
            fos.write(bytedFile);
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
