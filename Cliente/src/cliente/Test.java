/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author redes
 */
public class Test {


//------------------------------------------------------------------------------
  public static void main(String arg[])
  {
      try {
          Server servidor = new Server();
          Client cliente = new Client();


          InputStreamReader isr = new InputStreamReader(System.in);
          BufferedReader br = new BufferedReader(isr);
          int opcion=0;
          double tam ;
          String nombre,path,ip,port;
          ip = InetAddress.getLocalHost().getHostAddress();
          port = String.valueOf(7895);
          //La parte servidor del cliente siempre debe estar escuchando
          System.out.println("Digite nombre del archivo");
          opciones();
          opcion = Integer.parseInt(br.readLine());
          do{
              switch(opcion)
              {
                  case 1 :

                      System.out.println("Digite nombre del archivo");
                      nombre =br.readLine();
                      path ="Archivos/"+nombre;
                      File f = new File (path);
                      if(f.exists())
                        tam = f.length();
                      else
                        tam = -1;
                      System.out.println("tam :"+tam+"nombre : "+nombre+"ip: "+ ip+"port: "+port+" path :"+path);
                      cliente.agregarArchivo(tam, nombre, ip,port, path);
                      break;
                  case 2 :

                      System.out.println("Digite el nombre del archivo");
                      nombre = br.readLine();
                     if(cliente.pedirDirectorio (nombre)==-1);
                      System.out.println("Ningun servidor tiene el archivo pedido");
                      break;
                  case 3 :

                      ArrayList<String> archivos = new ArrayList<>();
                      archivos = cliente.pedirTodos();
                      if (archivos.size()==0)
                          System.out.println("El servidor no contiene archivos en este momento");
                      for(String a : archivos)
                      {
                          System.out.println(a);
                      }

                      break;
              }
              opciones();
              opcion = Integer.parseInt(br.readLine());
          }
          while(opcion<4 && opcion>0);
      } catch (IOException ex) {
          Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
//------------------------------------------------------------------------------
      public static void opciones()
  {
      System.out.println("Seleciione una opcion ");
      System.out.println("1. Agregar archivo ");
      System.out.println("2. Descargar archivo");
      System.out.println("3. Mirar una lista con todos los archivos");
  }


}
