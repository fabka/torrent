/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import java.io.Serializable;

/**
 *
 * @author jimena
 */
public class Operacion implements Serializable {
    public final static boolean DESCARGAR = true;
    public final static boolean MODIFICAR = false;
    
    
    private String idArchivo;
    boolean operacion;


    public String getArchivo() {
        return idArchivo;
    }

    public void setArchivo(String idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Operacion(String idArchivo, boolean operacion) {
        this.idArchivo = idArchivo;
        this.operacion = operacion;
        
    }
       
    public boolean isOperacion() {
        return operacion;
    }

    public void setOperacion(boolean operacion) {
        this.operacion = operacion;
    }



}
