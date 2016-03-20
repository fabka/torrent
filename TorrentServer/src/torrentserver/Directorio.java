/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Fabi
 */
public final class Directorio{
    
    private final ConcurrentHashMap<Archivo, Torrent> directorio;
    private ColaDirectorios colaDirectorios;

    public Directorio() {
        this.colaDirectorios = new ColaDirectorios();
        this.directorio = load();
    }
    
    private ConcurrentHashMap<Archivo, Torrent> load(){
        ConcurrentHashMap<Archivo, Torrent> directorio;
        try{
            FileInputStream fin = new FileInputStream("c:\\address.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            directorio = (ConcurrentHashMap<Archivo,Torrent>) ois.readObject();
            ois.close();
            System.out.println("Directorio cargado correctamente");
            return directorio;
        }catch(IOException | ClassNotFoundException ex){
            System.out.println("Error al cargar el directorio");
            return null;
        }
    }
    
    public List<Zocalo> clientesDisponibles(String nombre){
        Set<Archivo> archivos = directorio.keySet();
        for(Archivo a: archivos){
            if(a.getNombre().equals(nombre)){
                Torrent t =  directorio.get(a);
                return t.getZocalos();
            }
        }
        return null;
    }
    
    public void anadirArchivo ( Archivo archivo ){
        AnadirAchivo anadirAchivo = new AnadirAchivo(archivo);
        colaDirectorios.guardar();
    }
    
    public List<Archivo> obtenerArchivosDisponibles(){
        List archivos = new ArrayList<>();
        archivos.addAll(directorio.keySet());
        return archivos;
    }    
    
    public void anadirZocalo ( String hash, String ip, String puerto ){
        Archivo archivo = new Archivo(hash);
        Zocalo zocalo = new Zocalo(ip, puerto);
        AnadirZocalo anadirZocalo = new AnadirZocalo(archivo, zocalo);
    }
    
    public void anadirZocalo ( Zocalo z, Archivo a ){
        AnadirZocalo anadirZocalo = new AnadirZocalo(a, z);
    }
    
    public Torrent obtenerTorrent(Archivo archivo){
        return directorio.get(archivo);
    }    
    private class AnadirAchivo implements Runnable{
        Archivo archivo;

        public AnadirAchivo( Archivo archivo) {
            this.archivo =  archivo;
        }
        
        @Override
        public void run() {
            Torrent torrent = new Torrent(archivo);
            directorio.put(archivo, torrent);
        }
    }     
    
    private class AnadirZocalo implements Runnable{
        Archivo archivo;
        Zocalo zocalo;

        public AnadirZocalo( Archivo archivo, Zocalo zocalo) {
            this.archivo =  archivo;
            this.zocalo = zocalo;
        }
        
        @Override
        public void run() {
            Set<Archivo> archivosDisponibles = directorio.keySet();
            if(!archivosDisponibles.contains(this.archivo))
                anadirArchivo(archivo);
            Torrent torrent = directorio.get(archivo);
            torrent.anadirZocalo(zocalo);
        }
    }
    
    private class ColaDirectorios implements Runnable{
        private ConcurrentLinkedQueue<ConcurrentHashMap<Archivo, Torrent>> colaDirectorios;

        public ColaDirectorios() {
            this.colaDirectorios = new ConcurrentLinkedQueue<>();
        }
        
        public void guardar(){
            this.colaDirectorios.add(directorio);
        }

        @Override
        public void run() {
            while(true){
                ConcurrentHashMap<Archivo, Torrent> d = colaDirectorios.peek();
                if ( d != null ){
                    try{
                        FileOutputStream fos = new FileOutputStream("directorio.fabi");
                        ObjectOutputStream oos = new ObjectOutputStream(fos); 
                        oos.writeObject(d);
                        oos.close();
                    }catch(Exception ex){
                    }
                }
            }
        }
    }
}
