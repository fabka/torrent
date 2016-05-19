/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torrentserver;

import java.io.Serializable;
import java.util.Objects;

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

    public Zocalo(String ip, int puerto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Zocalo ){
            Zocalo z = (Zocalo)obj;
            return this.ip.equals(z.getIp()) && this.puerto.equals(z.getPuerto());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.ip);
        hash = 41 * hash + Objects.hashCode(this.puerto);
        return hash;
    }
    
        
}
