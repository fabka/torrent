/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.io.Serializable;

/**
 *
 * @author Fabi
 */
public class Zocalo implements Serializable{
    private String ip;
    private String puerto;

    public Zocalo(String ip, String puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
}
