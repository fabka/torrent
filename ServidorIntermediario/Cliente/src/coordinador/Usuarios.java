/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

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
    
    ConcurrentHashMap<String, String> usuarios = new ConcurrentHashMap<>();

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
        String p = usuarios.get(username);
        if( p == null && username != null && password != null){
            //System.out.println("Está guardando "+username+" y "+password);
            usuarios.put(username, password);
            save();
            return true;
        }
        return false;
    }

    public boolean iniciarSesion(String username, String password) {
        //System.out.println("username:"+username+" password:"+password);
        String contrasena = usuarios.get(username);
        if( contrasena != null )
            if( contrasena.equals(password) )
                return true;
        return false;
    }
    
    public ArrayList<String> usuarios(){
        System.out.println("llamado a usuarios()...");
        Set<String> set = usuarios.keySet();
        System.out.println("size = "+set.size());
        for(String s: set){
            System.out.println("- "+s+":"+usuarios.get(s));
        }
        return new ArrayList<>(set);
    }
    
    private ConcurrentHashMap<String, String> load(){
        ConcurrentHashMap<String, String> d = null;
        try{
            FileInputStream fin = new FileInputStream("users.dir");
            ObjectInputStream ois = new ObjectInputStream(fin);
            ConcurrentHashMap<String, String> archivoCargado = (ConcurrentHashMap<String, String>) ois.readObject();
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
}