
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
    
    @Override
    public int suma(int a, int b) throws RemoteException {
        return a+b;
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
    
}
