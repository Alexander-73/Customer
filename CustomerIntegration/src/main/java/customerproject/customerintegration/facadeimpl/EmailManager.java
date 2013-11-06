/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerintegration.facadeimpl;

import customerproject.customerintegration.facades.EmailLocalManager;
import customerproject.customerintegration.services.EmailService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author timovaananen
 */
@Stateless
public class EmailManager implements EmailLocalManager {

    @EJB
    EmailService emailSender;
    
    @Override
    public boolean send(String emailTo, String hash) {
        
        return emailSender.send(emailTo, hash);
    }

  
}
