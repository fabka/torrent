/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.util.List;

/**
 *
 * @author Fabi
 */
public class Torrent {
    Archivo archivo;
    List<Zocalo> zocalos;

    public Torrent() {
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public void setZocalos(List<Zocalo> zocalos) {
        this.zocalos = zocalos;
    }
}
