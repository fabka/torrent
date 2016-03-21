package torrentserver;

import java.net.*;
import java.io.*;
import java.util.List;
public class TCPServer {
    public static void main (String args[]) {
        try{
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Servidor en línea");
            while(true) {
                System.out.println("Esperando petición del cliente...");
                Socket clientSocket = listenSocket.accept();
                String remoteIP = clientSocket.getRemoteSocketAddress().toString();
                System.out.println("Conexión establecida con "+remoteIP);
                Connection c = new Connection(clientSocket);
            }
        } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
}
class Connection extends Thread {
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;
    ManejadorDirectorio manejadorDirectorio;
    

    public Connection (Socket clientSocket) {
        try {
            manejadorDirectorio = new ManejadorDirectorio();
            this.clientSocket = clientSocket;
            in = new ObjectInputStream( this.clientSocket.getInputStream());
            out = new ObjectOutputStream( this.clientSocket.getOutputStream());
            this.start();
        }catch(IOException e) {System.out.println("Connection:"+e.getMessage());
        }finally{ System.out.println("Servidor fuera de línea"); } 
    }
        
    @Override
    public void run(){
        String nombre, comando;
        try {
            comando = in.readUTF();
            if( comando == null )
                comando = "";
            switch( comando ){
                case "obtener todos":
                    System.out.println("Obtener todos los archivos");
                    manejadorDirectorio.imprimirTodosArchivos(manejadorDirectorio.obtenerTodosArchivos());
                    //out.writeObject(manejadorDirectorio.obtenerTodosArchivos());
                    break;
                case "obtener":
                    nombre = in.readUTF();
                    System.out.println("Obtener c/s disponibles de "+nombre);
                    //out.writeObject(manejadorDirectorio.obtenerDisponibles(nombre));
                    break;
                case "agregar archivo":
                    System.out.println("Agregar archivo");
                    //manejadorDirectorio.agregarArchivo(in);
                    break;
            }
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {System.out.println("Conexión finalizada");clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
    
    private class ManejadorDirectorio{
        Directorio directorio;

        public ManejadorDirectorio() {
            this.directorio = new Directorio();
        }      
                
        public List<Archivo> obtenerTodosArchivos(){
            List<Archivo> lista = directorio.obtenerArchivosDisponibles();
            return lista;
        }
        
        public List<Zocalo> obtenerDisponibles( String nombre ){
            List<Zocalo> zocalos;
            zocalos = directorio.clientesDisponibles(nombre);
            return zocalos;
        }
        
        public void agregarArchivo( ObjectInputStream in ) throws IOException{
            String nombre = in.readUTF();
            String hash = in.readUTF();
            String ip = in.readUTF();
            String puerto = in.readUTF();
            String peso = in.readUTF();
            directorio.anadirZocalo(nombre, hash, ip, puerto, peso);
        }
        
        public void imprimirTodosArchivos( List<Archivo> archivos){
            directorio.imprimirArchivosDisponibles(archivos);
        }
                
    }
}
