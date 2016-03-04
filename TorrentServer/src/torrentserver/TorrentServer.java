/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Fabi
 */
public class TorrentServer implements Runnable{

    ConcurrentHashMap<Archivo, Torrent> directorio;
    Directorio manejadorDirectorio = new Directorio();

    public TorrentServer() {
        directorio = manejadorDirectorio.load();
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TorrentServer torrentServer = new TorrentServer();
    }
          

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
