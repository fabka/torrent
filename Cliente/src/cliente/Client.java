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
import java.util.logging.Logger;



/**
 *
 * @author redes
 */
public class Client  {
 private  int serverPort = 7895 ;
  private  int serverPortS = 7896 ;
 private String host = "localhost";



//-------------------------------------------------------------------------------------------------------
//conectarse con el directorio
public void pedirDirectorio (String archivo)
 {
    ArrayList<Zocalo> servidores = new ArrayList<>();
    Zocalo z= new Zocalo();
    String hash = null;
    DataInputStream in ;
    DataOutputStream out ;
    int partes=0,total;
    String ip, puerto;
       String ipS = "169.254.187.48";
    Socket s = null;
    try{
            s = new Socket(ipS,this.serverPortS);
                    in = new DataInputStream( s.getInputStream());
                    out =new DataOutputStream( s.getOutputStream());
                    out.writeUTF("obtener lista");
                    out.writeUTF(archivo);
                    partes = Integer.parseInt(in.readUTF());
                     hash=in.readUTF();
                      total = Integer.parseInt(in.readUTF());
                     for(int i=0; i<total; i++)
                    {
                        //se lee el nombre de cada archivo ip y puerto
                        z.setIp(InetAddress.getByName(in.readUTF()));
                        z.setPuerto(in.readUTF());

                        //se agrega a la lista de servidores
                        servidores.add(z);
                    }


    } catch (IOException ex) {
      Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
    }
    //Se llaama a la funcion que buscara los servidores donde debe descargar

    descargar(servidores, 20, partes, archivo, hash);
 }
//-------------------------------------------------------------------------------------------------------
   public void agregarArchivo(double size, String name, String ip, String port, String path)
   {

       Archivo a = new Archivo(name, size, path);
       try {
           DataOutputStream out ;
           Socket s = null;
           String ipS = "169.254.187.48";
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
            s = new Socket(host,this.serverPortS);
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
public void descargar (ArrayList<Zocalo> servidores, int tam, int partes, String nombre, String hash)//InetAddress ip, int port, int parte )
{
     try //InetAddress ip, int port, int parte )
     {
         Socket s = null;
         int numbytes, actual;
         FileOutputStream fos =null;
         double tamfinal=0;
         BufferedOutputStream bos = null;
         InputStream in  = null;
         try{
             
             System.out.println("conecto..");
             byte[] bytes= null;
             //-------------------------------------------------------------------------------
             //in = s.getInputStream();
             //        int partes =1;
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             //      public void MergeFileExample(String nombre, int partes) {
             //File ofile = new File("Archivos/"+nombre);
             
             FileInputStream fis;
             byte[] fileBytes;
             int bytesRead = 0, i =0;
             List<File> list = new ArrayList<File>();
             
             for ( i = 0; i <partes; i++) {
                 list.add(new File( "test" + i  + ".txt" ));
             }
             fos = new FileOutputStream("Archivos/"+nombre);
             for (File file : list)  {
                 File ofile = new File("Archivos/"+file.getName());
                 Random rnd = new Random();
                 int servidor = rnd.nextInt(servidores.size());
                 System.out.println("indice servidor "+servidor);
                 s = new Socket(servidores.get(servidor).getIp().getHostAddress(),/*Integer.parseInt(servidores.get(servidor).getPuerto())*/this.serverPort);
                 in = s.getInputStream();
                 DataOutputStream output = new DataOutputStream(s.getOutputStream());
                 output.writeUTF(String.valueOf(i));
                 output.writeUTF(hash);
                 bytes = new byte [tam*1024];
                 
                 in = s.getInputStream();
                 numbytes = in.read(bytes,0,bytes.length);
                 System.out.println(bytes);
                 System.out.println(bytes.length);
                 while (numbytes>0)
                 {
                     baos.write(bytes,0,numbytes);
                     numbytes=in.read(bytes);
                 }
                 ofile.delete();
                 baos.writeTo(fos);
             }
             
             fos.close();
             
         }catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage()); e.printStackTrace();
         }catch (EOFException e){System.out.println("EOF:"+e.getMessage()); e.printStackTrace();
         }catch (IOException e){System.out.println("readline:"+e.getMessage()); e.printStackTrace();
         
         }finally {
             try {
                 if(bos != null) bos.close();
                 if(fos != null) fos.close();
                 if(s != null) s.close();
             } catch (IOException ex) {
                 Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         File f = new File("Archivos/"+nombre);
         tamfinal = f.length();
         System.out.println("tam final"+tamfinal);
         agregarArchivo(tamfinal, nombre, InetAddress.getLocalHost().getHostAddress(), String.valueOf(7896), "Archivos/"+nombre);
     } catch (UnknownHostException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

//-------------------------------------------------------------------------------------------------------
}




