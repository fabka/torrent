/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author redes
 */
public class Test {

    String ip;
    String host;
    ArrayList<Archivo> archivos;

    public Test() {
        archivos = new ArrayList<>();
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  public void main(String args[]) throws IOException
  {
      Server server = new Server();
      Client client = new Client();
      String nombre;
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      int opcion = 0, tam; 
      //La parte servidor del cliente siempre debe estar escuchando
      server.listenSocket();
      
      
      
      opciones();
      opcion = Integer.parseInt(br.readLine());
      
      switch(opcion)
      {
          case 1 :
          {
              System.out.println("Digite nombre del archivo");
               nombre =br.readLine();
               System.out.println("Digite tamanio del archivo");
               tam = Integer.parseInt(br.readLine());
              server.agregarArchivo(tam, nombre, this.ip, this.host);
          }
          case 2 :
          {
               ArrayList<String> archivos = new ArrayList<>();
               archivos = client.pedirTodos();
               for(String a : archivos)
               {
                  System.out.println(a);
               }
               System.out.println("Digite el nombre de la pelicula");
               nombre = br.readLine();
              for(String a : archivos)
               {
                   if(nombre.compareTo(a)==0)  //arreglar a las cosas de java
                       client.pedirDirectorio(nombre);
               }
          }
          opciones();
            opcion = Integer.parseInt(br.readLine());  
      }
  }
  
  public void opciones()
  {
      System.out.println("Seleciione una opcion ");
      System.out.println("1. Agregar archivo ");
      System.out.println("2. Descargar archivo");
  }
}
