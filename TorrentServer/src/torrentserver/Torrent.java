/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabi
 */
public class Torrent {
    Archivo archivo;
    List<Zocalo> zocalos;

    public Torrent() {
        this.zocalos = new ArrayList<>();
    }
    
    public Torrent(Archivo archivo) {
        this.archivo = archivo;
        this.zocalos = new ArrayList<>();
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public void setZocalos(List<Zocalo> zocalos) {
        this.zocalos = zocalos;
    }
    
    public List<Zocalo> getZocalos() {
        return this.zocalos;
    }
    
    public void anadirZocalo( String ip, String puerto){
        Zocalo z = new Zocalo(ip, puerto);
        this.zocalos.add(z);
    }
    
    public void anadirZocalo( Zocalo zocalo){
        this.zocalos.add(zocalo);
    }
}
