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
               // Thread t = new Thread(client);
               //t.start();

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
               // parte = num_parte;
                //in = new DataInputStream( clientSocket.getInputStream());
                out = clientSocket.getOutputStream();

                this.start();
            } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}

	public void run(){
            String hash, nombre = null;
		try {
                    DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                    parte = Integer.parseInt(in.readUTF());
                    hash = in.readUTF();
                    Archivo a = new Archivo();

                        //necesario cambiar a buscar en la carpeta
                        File carpeta = new File ("Archivos");
                        File[] archivos = carpeta.listFiles();
                        for(File e : archivos)
                        {
                             if (a.md5("Archivos/"+e.getName()).equals(hash))
                                nombre = e.getName();
                        }


		int size = 1024;

                //enviar el archivo
		File testfile = new File("Archivos/"+nombre);
		 byte[] bytes ;
                Path path = Paths.get("Archivos/"+nombre);
                bytes = Files.readAllBytes(path);
                System.out.println("Size : "+ bytes.length);
                fis = new FileInputStream(testfile);
                bis = new BufferedInputStream(fis);
                bis.read(bytes,0,bytes.length);
                //-----------------------------------------------------------------------------
                 int fileSize = (int) testfile.length();
                 int nChunks = 0, readLength= size * 1024;
                 String newFileName;
                 double num_parte = Math.floor(fileSize/readLength)+1;
                 fis = new FileInputStream(testfile);
     	         while (fileSize > 0) {
   	  	     if (fileSize <= readLength) {
  		          readLength = fileSize;
   		        }
            	 	   bytes = new byte[readLength];
       	  	    	  //bytes = Files.readAllBytes(path);
       	    	 	   bis = new BufferedInputStream(fis);
                           bis.read(bytes,0,bytes.length);
       			      //  System.out.println("read "+ read);
       			   fileSize -= readLength;

  			    assert (readLength == bytes.length);
                            newFileName = "test.part" + nChunks;
 	                        nChunks++;
                       // + Integer.toString(nChunks - 1);
                             if (parte == nChunks)
                             {

                            out.write(bytes,0,bytes.length);
               		    out.flush();
                             }

            }
       //     System.out.println("Numero despues antes "+nChunks);
     //       inputStream.close();
        } catch (IOException exception) { exception.printStackTrace();
        } finally{ try {
                    clientSocket.close();
                    bis.close();
                    out.close();
                    fis.close();
                }catch (IOException e){System.out.println("close failed "+e.getMessage());}}


	}






}


//------------------------------------------------------------------------------
}
