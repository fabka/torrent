/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;


import java.nio.channels.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jimena
 */
public class RMIM {
    
    private void start(){
        try {
            Registry registry = LocateRegistry.createRegistry(6897);
            registry.rebind("Directorio", new RMIServer());
        } catch (RemoteException ex) {
            Logger.getLogger(RMIM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public RMIM(){
       
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RMIM main = new RMIM();
        main.start();
    }
    
}