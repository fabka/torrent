import java.net.*;
import java.io.*;
import java.util.Scanner;
public class TCPClient {
    public static void main (String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        String ip = "localhost";
        DataInputStream in;
        DataOutputStream out;
        try{
            
            int serverPort = 7896;
            s = new Socket(ip, serverPort);  
            out = new DataOutputStream( s.getOutputStream());
            in = new DataInputStream( s.getInputStream());            
            
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Ingrese comando");
            String myStr = keyboard.nextLine();
            System.out.println("Comando: "+myStr);
            while( !myStr.equals("salir") ){
                switch( myStr ){
                    case "agregar":
                        out.writeUTF("agregar");
                        out.writeUTF("nombreTest");
                        out.writeUTF("hashTest");
                        out.writeUTF("80000");
                        out.writeUTF("ipTest");
                        out.writeUTF("puertoTest");
                        break;
                    case "obtener todos":
                        out.writeUTF("obtener todos");
                        int tam = Integer.parseInt(in.readUTF());
                        for (int i = 0; i < tam; i++) {
                            System.out.println("*"+in.readUTF());
                        }
                        break;
                    case "obtener lista":
                        out.writeUTF("obtener lista");
                        System.out.println("Nombre del archivo");
                        String nombre = keyboard.nextLine();
                        out.writeUTF(nombre);
                        tam = Integer.parseInt(in.readUTF());
                        for (int i = 0; i < tam; i++) {
                            System.out.println("* Ip:"+in.readUTF()+" puerto:"+in.readUTF());
                        }
                        break;
               }
               myStr = keyboard.nextLine();
               System.out.println("Comando: "+myStr);
            }
            out.writeUTF("salir");
        }catch (UnknownHostException e){e.printStackTrace(); System.out.println("Socket:"+e.getMessage());
        }catch (EOFException e){e.printStackTrace(); System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){e.printStackTrace(); System.out.println("readline:"+e.getMessage());
        }finally {if(s!=null) try {s.close();}catch (IOException e){e.printStackTrace(); System.out.println("close:"+e.getMessage());}}
    }
}