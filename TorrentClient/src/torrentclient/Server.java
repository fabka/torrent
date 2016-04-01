/*
 * Servidor del lado cliente
*/
/**
 *
 * @author redes
 */

package torrentclient;

import torrentclient.ClientThread;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Server {

   private static final int port_start = 49152;
   private static final int port_end = 65534;
    private String host = "localhost";
   private int serverPort = 0 ;
 
    public Server() {
    }


//------------------------------------------------------------------------------    
  public  void listenSocket() throws IOException
  {
    int port = port_start;
    ServerSocket server = null; 
        for (; port <= Server.port_end; port++) 
        {
              server = new ServerSocket(port);
              this.serverPort = port;
        }
    while(true){
      ClientThread client;
        //accept espera client connection   
        client = new ClientThread(server.accept());
        Thread t = new Thread(client);
        t.start();
 
    }

  } 
    
//------------------------------------------------------------------------------  
   public void agregarArchivo(float tamanio, String name, String ip, String port)
   {
       try {
           ObjectOutputStream out ;
           Socket s = null;
           s = new Socket(host,this.serverPort);
           out =new ObjectOutputStream( s.getOutputStream());
           out.writeUTF("agregar");
           //hash
           out.writeUTF(ip);
           out.writeUTF(port);
           out.writeUTF(tamanio+"");
           //Arreglar
           out.writeUTF(name);
           out.close();
       } catch (IOException ex) {
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
//------------------------------------------------------------------------------  