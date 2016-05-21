/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import coordinador.RMIServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabi y Jime
 */

public class ServerM  {
    
    private void start(){
        try {
            Registry registry = LocateRegistry.createRegistry(6898);
            registry.rebind("Servidor", new Server());
        } catch (RemoteException ex) {
            Logger.getLogger(ServerM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ServerM(){
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerM main = new ServerM();
        main.start();
    }
    
}