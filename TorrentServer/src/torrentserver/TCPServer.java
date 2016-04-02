package torrentserver;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
public class TCPServer {
    
    public static void main (String args[]) {
        
        Directorio directorio = new Directorio();
        
        try{
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket, directorio);
            }
        } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());
        } finally { directorio.close(); }
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    Directorio directorio;
    
    public Connection (Socket aClientSocket, Directorio directorio) {
            try {
                this.directorio = directorio;
                clientSocket = aClientSocket;
                in = new DataInputStream( clientSocket.getInputStream());
                out =new DataOutputStream( clientSocket.getOutputStream());
                this.start();
            } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }

    @Override
    public void run(){
        String comando, nombre, hash, ip, puerto, peso_string;
        try {			            
                comando = in.readUTF();
                
                switch(comando){
                case "agregar":
                    nombre = in.readUTF();
                    hash = in.readUTF();
                    peso_string = in.readUTF();
                    ip = in.readUTF();
                    puerto = in.readUTF();
                    directorio.anadirZocalo(nombre, hash, Double.parseDouble(peso_string), ip, puerto);
                    break;
                case "obtener todos":
                    ArrayList<Archivo> archivos = directorio.obtenerArchivos();
                    out.writeUTF(archivos.size()+"");
                    for(Archivo a: archivos)
                        out.writeUTF(a.getNombre());
                    break;
                case "obtener lista":
                    nombre = in.readUTF();
                    Archivo a = directorio.obtenerArchivo(nombre);
                    ArrayList<Zocalo> zocalos = directorio.obtenerListaZocalos(a.getHash());
                    out.writeUTF(zocalos.size()+"");
                    for(Zocalo z: zocalos){
                        out.writeUTF(z.getIp());
                        out.writeUTF(z.getPuerto());
                    }
                break;
            }
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}
