/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Fabi
 */
public class ColaDirectorios implements Runnable{
    private ConcurrentLinkedQueue<ConcurrentHashMap<Archivo, Torrent>> colaDirectorios;

    public ColaDirectorios() {
        this.colaDirectorios = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        
    }
}
