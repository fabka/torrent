/*
 * Servidor del lado cliente
*/
/**
 *
 * @author redes
 */

package cliente;


import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {


   List<Archivo> archivos;
   private String host = "localhost";
   private int serverPort = 7895 ;

    public Server() {
           start();
    }

	public void run(){
             listenSocket();
        }

//------------------------------------------------------------------------------
  public  void listenSocket()
  {
       try {
           ServerSocket serverSocket = new ServerSocket(this.serverPort);
           while(true){
               Connection client;
               //accept espera client connection
               Socket cliente = serverSocket.accept();
               client = new Connection(cliente);

           }  } catch (IOException ex) {
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
       }

  }

//------------------------------------------------------------------------------
 class Connection extends Thread {
  //DataInputStream in;
  OutputStream out;
  Socket clientSocket;
        BufferedInputStream bis = null;
        FileInputStream fis= null;
        int size = 1;
        int parte =0;

  public Connection (Socket aClientSocket) {
            try{
                clientSocket = aClientSocket;
                out = clientSocket.getOutputStream();
                this.start();
            } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
  }

  public void run(){
            String hash, nombre = null;
    try {
                    int nChunks = 0, readLength= 2 * 1024, parte, read;
                    
                    DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                    parte = Integer.parseInt(in.readUTF());
                    
                    hash = in.readUTF();
                    
                    Archivo a = new Archivo();
                    File carpeta = new File ("Archivos");
                    File[] archivos = carpeta.listFiles();
                    for(File e : archivos)
                    {
                        if (a.md5("Archivos/"+e.getName()).equals(hash))
                        nombre = e.getName();
                    }
                    
                    File testfile = new File("Archivos/"+nombre);
                    int fileSize = (int) testfile.length();
                     int partes = ((int)(fileSize/readLength))+1;
                    // System.out.println("Partes"+partes);
                    
              //      DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                   
                    ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
                    os.flush();

                    byte[] bytes ;
        
                    fis = new FileInputStream(testfile);
                    bis = new BufferedInputStream(fis);

                    FileOutputStream filePart ;

                   while (fileSize > 0) {
                   if (fileSize <= readLength) {
                    readLength = fileSize;
                   }
                    bytes = new byte[readLength];
                    read = fis.read(bytes,0,readLength);
                   fileSize -= read;

                   assert (read == bytes.length);
                   if(parte == nChunks) 
                   {
                      os.writeObject(bytes); 
                    }

                   nChunks++;
                    }

                }catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

}

//------------------------------------------------------------------------------
}
