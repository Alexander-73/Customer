/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerintegration.facades;

import javax.ejb.Local;

/**
 *
 * @author timovaananen
 */
@Local
public interface EmailLocalManager {
    
    public boolean send(String emailTo, String hash);
}