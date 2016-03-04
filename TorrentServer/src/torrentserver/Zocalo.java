/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

/**
 *
 * @author Fabi
 */
public class Zocalo {
    private String ip;
    private int puerto;

    public Zocalo(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    
    
}
