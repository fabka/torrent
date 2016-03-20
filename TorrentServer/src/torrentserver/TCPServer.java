package torrentserver;
import java.net.*;
import java.io.*;
import java.util.List;
public class TCPServer {
    public static void main (String args[]) {
            try{
                    int serverPort = 7896; // the server port
                    ServerSocket listenSocket = new ServerSocket(serverPort);
                    while(true) {
                            Socket clientSocket = listenSocket.accept();
                            Connection c = new Connection(clientSocket);
                    }
            } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
}
class Connection extends Thread {
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket clientSocket;
    Directorio directorio = new Directorio();

    public Connection (Socket aClientSocket) {
            try {
                    clientSocket = aClientSocket;
                    in = new ObjectInputStream( clientSocket.getInputStream());
                    out =new ObjectOutputStream( clientSocket.getOutputStream());
                    this.start();
            } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }

    @Override
    public void run(){
        String nombre, comando, hash, ip, puerto, peso;
        try {			                 // an echo server
                comando = in.readUTF();
                if( comando!=null && comando.equals("obtener")){
                    nombre = in.readUTF();
                    if( nombre.equals("todos")){
                        List<Archivo> lista = directorio.obtenerArchivosDisponibles();
                        out.writeObject(lista);
                    }else{
                        List<Zocalo> zocalos;
                        zocalos = directorio.clientesDisponibles(nombre);
                        out.writeObject(zocalos);
                    }
                }else if( comando!=null && comando.equals("agregar") ){
                    hash = in.readUTF();
                    ip = in.readUTF();
                    puerto = in.readUTF();
                    peso = in.readUTF();
                    directorio.anadirZocalo(hash, ip, puerto);
                }
                
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}
