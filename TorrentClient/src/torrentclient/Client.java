/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentclient;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author redes
 */
public class Client {
 private  int serverPort = 7896 ;
 private String host = "localhost";
 


//-------------------------------------------------------------------------------------------------------
//conectarse con el directorio 
public void pedirDirectorio (String archivo)
 {
    ArrayList<Zocalo> servidores = new ArrayList<>();
    ObjectInputStream in ;
    ObjectOutputStream out ;
    int partes;
    Socket s = null;
    try{
            s = new Socket(host,this.serverPort);    
                    in = new ObjectInputStream( s.getInputStream());
                    out =new ObjectOutputStream( s.getOutputStream());
                    out.writeUTF("obtener");   
                    out.writeUTF(archivo);      	
                    servidores = (ArrayList<Zocalo>) in.readObject();	    // read a line of data from the stream
                    partes = (Integer) in.readObject() ;	
    } catch (IOException | ClassNotFoundException ex) {
      Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
    }
    //Se llaama a la funcion que buscara los servidores donde debe descargar
    descargar(servidores);
 }
//-------------------------------------------------------------------------------------------------------
//conectarse con el directorio 
public  ArrayList<Archivo>  pedirTodos ()
 {
    ArrayList<Archivo> archivos = new ArrayList<>();
    ObjectInputStream in ;
    ObjectOutputStream out ;
    Socket s = null;
    try{
            s = new Socket(host,this.serverPort);    
                    in = new ObjectInputStream( s.getInputStream());
                    out =new ObjectOutputStream( s.getOutputStream());
                    out.writeUTF("obtener");   
                    out.writeUTF("todo");      	
                    archivos = (ArrayList<Archivo>) in.readObject();	    // read a line of data from the stream	
    } catch (IOException | ClassNotFoundException ex) {
      Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
     
    }
    return archivos;
 }
 //-------------------------------------------------------------------------------------------------------
public void descargar (ArrayList<Zocalo> servidores)
{
    for (Zocalo z : servidores)
    {
            if(verificarConexion(z.getIp())) 
                conectar(z.getIp(),z.getPuerto(),1);

    }
}
 //------------------------------------------------------------------------------------------------------
public boolean verificarConexion(InetAddress ip) 
{
    boolean conectado = false;
     try {
         // Ip de la máquina remota
         if (ip.isReachable(5000))
             conectado= true;
     } catch (IOException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
     }
     return conectado;
	
}
 //------------------------------------------------------------------------------------------------------
public void conectar (InetAddress ip, int port, int parte )
{
    ObjectInputStream in ;
    ObjectOutputStream out ;
    Socket s = null;
    Object obj;
    try{
            s = new Socket(host,port);    
                    in = new ObjectInputStream( s.getInputStream());
                    out =new ObjectOutputStream( s.getOutputStream());
                    out.writeObject(parte);      	
                    obj = in.readObject();	    // read a line of data from the stream
    }catch (IOException | ClassNotFoundException ex) {
      Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
}
//-------------------------------------------------------------------------------------------------------
}


    

