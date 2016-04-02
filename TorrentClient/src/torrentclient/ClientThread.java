/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package torrentclient;



/**
 *
 * @author redes
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread implements Runnable {
  protected Socket client;
  protected  boolean run = true; //get y set


//--------------------------------------------------------------------------------  
    ClientThread(Socket client){
      this.client = client;
     
      //no se si esto deba quedar
    }
//------------------------------------------------------------------------------
  @Override
    public void run(){
    try {
        ObjectInputStream in = null;
        ObjectOutputStream output = null;
        int parte;

        in = new ObjectInputStream(client.getInputStream());
        output = new ObjectOutputStream(client.getOutputStream());   
        parte = (int) in.readObject();
        output.writeObject("Hi Client ");
        //Se deberia enviar la parte que el cliente esta pidiendo del archivo

          in.close();
        output.close();
    } catch (IOException | ClassNotFoundException ex) {
        Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    }
 
}

    
//------------------------------------------------------------------------------
    
    

