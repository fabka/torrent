/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import cliente.Zocalo;
import static java.awt.PageAttributes.MediaType.A;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Monica
 */
public class Usuarios {
    
    ConcurrentHashMap<String, Usuario> usuarios = new ConcurrentHashMap<>();

    public Usuarios() {
        try {
            File f = new File("users.dir");
            if(!f.exists()) {
                f.createNewFile();
                FileOutputStream fout = new FileOutputStream("users.dir");
		ObjectOutputStream oos = new ObjectOutputStream(fout); 
                usuarios = new ConcurrentHashMap<>();
		oos.writeObject(usuarios);
		oos.close();
            }else{
                usuarios = load();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean agregarUsuario(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if( usuario == null && username != null && password != null){
            //System.out.println("Está guardando "+username+" y "+password);
            String ip = "obtener ip";
            String puerto = 6897+"";
            Zocalo z = new Zocalo(ip, puerto);
            boolean esActivo = true;
            usuario = new Usuario(username, password, z, false);
            usuarios.put(username, usuario);
            save();
            return true;
        }
        return false;
    }

    public boolean iniciarSesion(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if( usuario == null )
            return false;
        String contrasena = usuario.getPassword();
        if( contrasena == null )
            return false;
        else if( contrasena.equals(password) ){
            usuario.setEsActivo(true);
            return true;        
        }
        return false;
    }
    
    public ArrayList<String> usuarios(){
        return new ArrayList<>(usuarios.keySet());
    }
    
    public void verUsuarios(){
        System.out.println("llamado a usuarios()...");
        Set<String> set = usuarios.keySet();
        System.out.println("size = "+set.size());
        for(String s: set){
            System.out.println("- "+s+":"+usuarios.get(s));
        }
    }
    
    private ConcurrentHashMap<String, Usuario> load(){
        ConcurrentHashMap<String, String> d = null;
        try{
            FileInputStream fin = new FileInputStream("users.dir");
            ObjectInputStream ois = new ObjectInputStream(fin);
            ConcurrentHashMap<String, Usuario> archivoCargado = (ConcurrentHashMap<String, Usuario>) ois.readObject();
            ois.close();
            return archivoCargado;
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error al cargar el directorio");
            e.printStackTrace();
            return null;
        }
    }
    
    private void save(){
        try{
            FileOutputStream fout = new FileOutputStream("users.dir");
            ObjectOutputStream oos = new ObjectOutputStream(fout); 
            oos.writeObject(this.usuarios);
            oos.close();
        } catch (Exception e) {
        }
    }

    public ArrayList<Zocalo> obtenerActivos() {
        Set<String> users = usuarios.keySet();
        ArrayList<Zocalo> zocalos = new ArrayList<>();
        for(String s: users){
            Usuario usuario = usuarios.get(s);
            boolean activo = usuario.esActivo();
            if( activo )
                zocalos.add(usuario.getZocalo());
        }
        return zocalos;
    }
}