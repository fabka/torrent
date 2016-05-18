package torrentserver;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;
public class TCPServer {
    
    public static void main (String args[]) {
        Directorio directorio = new Directorio();
        try{
            System.out.println("Iniciando servidor...");
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
                out = new DataOutputStream( clientSocket.getOutputStream());
                this.start();
                System.out.println("Conexion establecida con "+clientSocket.getInetAddress().toString());
            } catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
    }

    @Override
    public void run(){
        String comando, nombre, hash, ip, puerto, peso_string;
        try {			       
                
            do{
                System.out.println("Esperando respuesta...");
                comando = in.readUTF();
                System.out.println("Recibido: " + comando);
                switch(comando){
                    case "agregar":
                        nombre = in.readUTF();
                        hash = in.readUTF();
                        peso_string = in.readUTF();
                        ip = in.readUTF();
                        puerto = in.readUTF();
                        System.out.println(nombre+","+ hash+","+ Double.parseDouble(peso_string)+","+ip+","+puerto);
                        directorio.anadirZocalo(nombre, hash, Double.parseDouble(peso_string), ip, puerto);
                        directorio.save();
                        System.out.println("Se añadio el archivo "+nombre+" correctamente");
                        break;
                    case "obtener todos":
                        ArrayList<Archivo> archivos = directorio.obtenerArchivos();
                        out.writeUTF(archivos.size()+"");
                        for(Archivo a: archivos){
                            out.writeUTF(a.getNombre());
                        }
                        break;
                    case "obtener lista":
                        nombre = in.readUTF();
                        Archivo a = directorio.obtenerArchivo(nombre);
                        if (a != null){
                            out.writeUTF(a.numeroPartes() + "");
                            System.out.println(" numero partes "+ a.numeroPartes());
                            System.out.println(" numero partes "+ a.getPeso());
                            out.writeUTF(a.getHash()); 
                            Zocalo zocalo = new Zocalo(clientSocket.getInetAddress().toString().replace("/",""), 7895+"");
                            ArrayList<Zocalo> zocalos = directorio.obtenerListaZocalos(a.getHash());
                            int tam = zocalos.size();
                            if (zocalos.contains(zocalo))
                                tam--;        
                            out.writeUTF(tam+"");
                            for(Zocalo z: zocalos){
                                if(!z.equals(zocalo)){
                                    out.writeUTF(z.getIp());
                                    out.writeUTF(z.getPuerto());
                                }
                            }                            
                        }else{
                            out.writeUTF("-1");
                        }
                    break;
                    case "ver directorio":
                        archivos = directorio.obtenerArchivos();
                        System.out.println("Tam archivos = "+archivos.size());
                        for( Archivo archivo: archivos ){
                            System.out.println("Nombre: "+archivo.getNombre());
                            System.out.println("hash  : "+archivo.getHash());
                            System.out.println("peso  : "+archivo.getPeso());
                            ArrayList<Zocalo> zocalos = directorio.obtenerListaZocalos(archivo.getHash());
                            for(Zocalo z: zocalos){
                                System.out.println("\t"+z.getIp()+":"+z.getPuerto());
                            }
                        }
                    break;        
                }
            }while(!comando.equals("salir"));
        }catch (EOFException e){System.out.println("EOFException: "+e.getMessage()); //e.printStackTrace();
        } catch(IOException e) {System.out.println("IOException: "+e.getMessage()); //e.printStackTrace();
        } finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
    }
}
