/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coordinador;

import cliente.Zocalo;
import java.io.Serializable;

/**
 *
 * @author redes
 */
public class Usuario implements Serializable{
    
    private String username;
    private String password;
    private Zocalo zocalo;
    private boolean activo;

    public Usuario(String username, String password, Zocalo zocalo, boolean esActivo) {
        this.username = username;
        this.password = password;
        this.zocalo = zocalo;
        this.activo = esActivo;
    }
    
    public String getPassword() {
        return password;
    }

    public Zocalo getZocalo() {
        return zocalo;
    }

    public void setZocalo(Zocalo zocalo) {
        this.zocalo = zocalo;
    }

    public boolean esActivo() {
        return activo;
    }

    public void setEsActivo(boolean esActivo) {
        this.activo = esActivo;
    }

    @Override
    public String toString() {
        return "Usuario{" + "password=" + password + ", zocalo=" + zocalo + ", activo=" + activo + '}';
    }    
}
