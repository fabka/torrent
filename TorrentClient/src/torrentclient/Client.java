/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

//import Manejador.TCPClient;
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
public class Client {
 private  int serverPort = 7896 ;
 private String host = "localhost";
 


//-------------------------------------------------------------------------------------------------------
//conectarse con el directorio 
public void pedirDirectorio (String archivo)
 {
    ArrayList<Zocalo> servidores = new ArrayList<>();
    Zocalo z= new Zocalo();
    DataInputStream in ;
    DataOutputStream out ;
    int partes=0,total;
    String ip, puerto;
    Socket s = null;
    try{
            s = new Socket(host,this.serverPort);    
                    in = new DataInputStream( s.getInputStream());
                    out =new DataOutputStream( s.getOutputStream());
                    out.writeUTF("obtener lista");   
                    out.writeUTF(archivo);      	
                    total = Integer.parseInt(in.readUTF()); 
                             for(int i=0; i<total; i++)
                    {
                        //se lee el nombre de cada archivo ip y puerto
                        z.setIp(InetAddress.getByName(in.readUTF()));
                        z.setPuerto(in.readUTF());
                        //se agrega a la lista de servidores
                        servidores.add(z);
                    }
                    
                    partes = Integer.parseInt(in.readUTF()) ;	
    } catch (IOException ex) {
      Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
    }
    //Se llaama a la funcion que buscara los servidores donde debe descargar
    
    descargar(servidores, 1, partes, archivo);
 }
//-------------------------------------------------------------------------------------------------------
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
            s = new Socket(host,this.serverPort);    
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
      Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
     
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
public void descargar (ArrayList<Zocalo> servidores, int tam, int partes, String nombre)//InetAddress ip, int port, int parte )
{
                Socket s = null;
                int numbytes, actual;
                FileOutputStream fos =null;
                BufferedOutputStream bos = null;
                InputStream in  = null;
		try{
                    //int tam = 1024;
			   
                      //  System.out.println("conecto..");
                        byte[] bytes= null;
                        //-------------------------------------------------------------------------------
                        in = s.getInputStream();
                //        int partes =1;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  //      public void MergeFileExample(String nombre, int partes) {
                        File ofile = new File("index2.jpg");
                        
                        FileInputStream fis;
                        byte[] fileBytes;
                        int bytesRead = 0, i =0;
                        List<File> list = new ArrayList<File>();
                        
                           for ( i = 0; i <partes; i++) {
                                list.add(new File( "test" + i  + ".txt" ));
                            }
                           fos = new FileOutputStream(nombre);
                        for ( i = 0; i <partes; i++) {
                            
                            Random rnd = new Random();
                            int servidor = rnd.nextInt(servidores.size());
                            s = new Socket(servidores.get(servidor).getPuerto(),Integer.parseInt(servidores.get(servidor).getIp().getHostAddress())); 
                            DataOutputStream output = new DataOutputStream(s.getOutputStream());   
                            output.writeUTF(String.valueOf(i));
                             bytes = new byte [tam*2];                            
                            fos = new FileOutputStream(ofile, true);
                            for (File file : list) {
                                in = s.getInputStream();
                                numbytes = in.read(bytes,0,bytes.length);
                                bos  = new BufferedOutputStream(fos);
                                while (numbytes>0)
                                {
                                    baos.write(bytes,0,numbytes);
                                    numbytes=in.read(bytes);
                                }
                                 baos.writeTo(fos);                            
                            }
                            fos.close();
                            fos = null;
                        }
                            
		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {
                    try {
                        if(bos != null) bos.close();
                        if(fos != null) fos.close();
                        if(s != null) s.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
     }

//-------------------------------------------------------------------------------------------------------
}


    

