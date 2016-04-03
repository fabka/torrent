/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabi
 */
public class Hash {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
    }
    
    public static String md5 (String ruta){
        try {
            FileInputStream fis;
            fis = new FileInputStream(new File(ruta));
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
            fis.close();
            return md5;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hash.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
