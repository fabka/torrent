package rmiClient;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
public class RMIClient {
    public static void main(String args[]){
        RMIClient rmiclient= new RMIClient();
        rmiclient.conectServer();
    }

    private void conectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost",1099);
            RMI rmi = (RMI)reg.lookup("server");
            System.out.println("Conecting to server...");
        } catch (Exception e) {
            
        }
    }
}
