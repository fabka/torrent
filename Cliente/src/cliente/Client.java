/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

//import Manejador.TCPClient;
import cliente.Server.Connection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;



/**
 *
 * @author redes
 */
public class Client  {
 private  int serverPort = 7895 ;
  private  int serverPortS = 7896 ;
 private String host = "localhost";

 private String ipS = "169.254.187.48";



//-------------------------------------------------------------------------------------------------------
//conectarse con el directorio
public int pedirDirectorio (String archivo)
 {
    ArrayList<Zocalo> servidores = new ArrayList<>();
    Zocalo z= new Zocalo();
    String hash = null;
    DataInputStream in ;
    DataOutputStream out ;
    int partes=0,total, tam;

    Socket s = null;
    try{
            s = new Socket(ipS,this.serverPortS);
                    in = new DataInputStream( s.getInputStream());
                    out =new DataOutputStream( s.getOutputStream());
                    out.writeUTF("obtener lista");
                    out.writeUTF(archivo);
                    partes = Integer.parseInt(in.readUTF());
                    if (partes == -1 )
                        return -1;
                     hash=in.readUTF();
                      total = Integer.parseInt(in.readUTF());

                     for(int i=0; i<total; i++)
                    {
                        z.setIp(InetAddress.getByName(in.readUTF()));
                        z.setPuerto(in.readUTF());
                        servidores.add(z);
                    }


    } catch (IOException ex) {
      Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
    }
    //Se llaama a la funcion que buscara los servidores donde debe descargar

    descargar(servidores, 1, partes, archivo, hash);
 return 1;
 }
//-------------------------------------------------------------------------------------------------------
   public void agregarArchivo(double size, String name, String ip, String port, String path)
   {

       Archivo a = new Archivo(name, size, path);

       try {
           DataOutputStream out ;
           Socket s = null;
           s = new Socket(ipS,this.serverPortS);
           out =new DataOutputStream( s.getOutputStream());
           out.writeUTF("agregar");
           out.writeUTF(name);
           out.writeUTF(a.getHash());
           out.writeUTF(String.valueOf(size));
           out.writeUTF(ip);
           out.writeUTF(port);

           out.close();
       } catch (IOException ex) {
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

//---------------------------------------------------------------------------------
//conectarse con el directorio
public  ArrayList<String>  pedirTodos ()
 {
    ArrayList<String> archivos = new ArrayList<>();
    DataInputStream in ;
    DataOutputStream out ;
    String aux;
    int total;
    Socket s = null;
    try{
            s = new Socket(ipS,this.serverPortS);
                    in = new DataInputStream( s.getInputStream());
                    out =new DataOutputStream( s.getOutputStream());
                    out.writeUTF("obtener todos");
         //Ingresa el tamañao de la losta
         total = Integer.parseInt(in.readUTF());
         for(int i=0; i<total; i++)
         {
             //se lee el nombre de cada archivo
             archivos.add(in.readUTF());
         }

     	    // read a line of data from the stream
    } catch (IOException ex) {
      Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);

    }
    return archivos;
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
public void descargar (List<Zocalo> servidores, int tam, int partes, String nombre, String hash)
{
    System.out.println("El numero de partes segun el directorio"+partes);
       Socket s = null;
       int numbytes, actual,i, servidor, bytesRead,tamfinal=0;
       FileOutputStream fos =null;
       BufferedOutputStream bos = null;
       InputStream in  = null;
       File ofile = null ;
         try{
            byte[] bytes ;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     //-------------------------------------------------------------------------------
            ofile = new File (nombre);

            FileInputStream fis;
            fos = new FileOutputStream("Archivos/"+ofile);

            for ( i = 0; i <partes; i++) {
            servidor = ThreadLocalRandom.current().nextInt(0, servidores.size());
           s = new Socket(servidores.get(servidor).getIp().getHostAddress(),this.serverPort);
          //escribir la parte que queremos
            DataOutputStream output = new DataOutputStream(s.getOutputStream());
            output.writeUTF(String.valueOf(i));

             output.writeUTF(hash);

            ObjectInputStream is;
            is = new ObjectInputStream(s.getInputStream());

            bytes = new byte [2048];

            bytes = (byte[]) is.readObject();
            System.out.println("tam bytes"+bytes.length);
            tamfinal+=bytes.length;
           //Lo escribimos en el archivo
            fos.write(bytes);
            fos.flush();
            bytes = null;
            }

            fos.close();
            fos = null;
        } catch (FileNotFoundException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
     }
//         tamfinal = (int) ofile.length();

     try {
         agregarArchivo(tamfinal, nombre, InetAddress.getLocalHost().getHostAddress(), String.valueOf(7895), "Archivos/"+nombre);
     } catch (UnknownHostException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
     }

}

//-------------------------------------------------------------------------------------------------------
}




