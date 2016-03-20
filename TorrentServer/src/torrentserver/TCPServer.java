package torrentserver;
import java.net.*;
import java.io.*;
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
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    Directorio directorio = new Directorio();

    public Connection (Socket aClientSocket) {
            try {
                    clientSocket = aClientSocket;
                    in = new DataInputStream( clientSocket.getInputStream());
                    out =new DataOutputStream( clientSocket.getOutputStream());
                    this.start();
            } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }

    @Override
    public void run(){
        String comando, hash, ip, puerto, peso;
        try {			                 // an echo server
                comando = in.readUTF();
                hash = in.readUTF();
                if( comando!=null && comando.equals("obtener")){
                    directorio.clientesDisponibles(hash);
                }else if( comando!=null && comando.equals("agregar") ){
                    ip = in.readUTF();
                    puerto = in.readUTF();
                    directorio.anadirZocalo(hash, ip, puerto);
                }
                
                //read a line of data from the stream
                out.writeUTF(comando);
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}
