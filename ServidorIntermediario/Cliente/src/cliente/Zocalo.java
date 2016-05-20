/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabi
 */
public class Zocalo implements Serializable {
    private InetAddress ip;
    private String puerto;

    public Zocalo(String ip, String puerto) {
        try {
            this.puerto = puerto;
            this.ip = InetAddress.getByName(ip);
        } catch (UnknownHostException ex) {
          //  Logger.getLogger(Zocalo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }


}
