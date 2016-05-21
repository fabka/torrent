/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Directory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import cliente.*;

/**
 *
 * @author Fabi
 */
public final class Directorio{
    
    private ConcurrentHashMap<Archivo, ArrayList<Zocalo>> directorio;

    public Directorio() {
        try {
            File f = new File("server.dir");
            if(!f.exists()) {
                f.createNewFile();
                FileOutputStream fout = new FileOutputStream("server.dir");
		ObjectOutputStream oos = new ObjectOutputStream(fout); 
                directorio = new ConcurrentHashMap<>();
		oos.writeObject(directorio);
		oos.close();
            }else{
                load();
            }
        } catch (Exception e) {
        }
    }
    
    private ConcurrentHashMap<Archivo, ArrayList<Zocalo>> load(){
        ConcurrentHashMap<Archivo, List<Zocalo>> d = null;
        try{
            FileInputStream fin = new FileInputStream("server.dir");
            ObjectInputStream ois = new ObjectInputStream(fin);
            directorio = (ConcurrentHashMap<Archivo, ArrayList<Zocalo>>) ois.readObject();
            ois.close();
            return directorio;
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error al cargar el directorio");
            e.printStackTrace();
            return null;
        }
    }    
    
    public void anadirZocalo ( String nombre, String path, double peso, String ip, String puerto ){
        AnadirZocalo anadirZocalo = new AnadirZocalo(new Archivo(nombre, peso, path), new Zocalo(ip, puerto));
    }
    
    public ArrayList<Archivo> obtenerArchivos(){
        return new ArrayList<>(directorio.keySet());
    }
    
    public Archivo obtenerArchivo( String nombre ){
        for( Archivo a: directorio.keySet() ){
            if(a.getNombre().equals(nombre))
                
                return a;
        }
        return null;
    }
    
    public ArrayList<Zocalo> obtenerListaZocalos(String hash){
        for( Archivo a: directorio.keySet() ){
            if(a.getHash().equals(hash))
                return directorio.get(a);
        }
        return null;
    }
    
    private class AnadirAchivo implements Runnable{
        Archivo archivo;

        public AnadirAchivo( Archivo archivo) {
            this.archivo =  archivo;
            run();
        }
        
        @Override
        public void run() {
            directorio.put(archivo, new ArrayList<Zocalo>());
        }
    }     
    
    private final class AnadirZocalo implements Runnable{
        Archivo archivo;
        Zocalo zocalo;

        public AnadirZocalo( Archivo archivo, Zocalo zocalo) {
            this.archivo =  archivo;
            this.zocalo = zocalo;
            run();
        }
        
        @Override
        public void run() {
            if( directorio == null )
                System.out.println("Directorio es nulo");
            Set<Archivo> archivosDisponibles = directorio.keySet();
            if(!archivosDisponibles.contains(this.archivo)){
                new AnadirAchivo(this.archivo);
            }
            ArrayList<Zocalo> zocalos = directorio.get(archivo);
            if( !zocalos.contains(this.zocalo))    
                directorio.get(this.archivo).add(zocalo);
        }
    }
    
    public void save(){
        try{
            FileOutputStream fout = new FileOutputStream("server.dir");
            ObjectOutputStream oos = new ObjectOutputStream(fout); 
            oos.writeObject(directorio);
            oos.close();
        } catch (Exception e) {
        }
    }
    
    public void close(){
        try {
            FileOutputStream fout = new FileOutputStream("server.dir");
            ObjectOutputStream oos = new ObjectOutputStream(fout); 
            oos.writeObject(directorio);
            oos.close();
        } catch (Exception e) {
        }
    }
}
