/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import static java.lang.Compiler.command;
import rmiServer.Directorio;

/**
 *
 * @author Fabi
 */
public class TorrentServer implements Runnable{

    Directorio manejadorDirectorio = new Directorio();

    public TorrentServer() {
    }
    
    /*public static void main(String[] args) {
        TorrentServer torrentServer = new TorrentServer();
    }*/
       
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
