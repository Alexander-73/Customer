/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerbusiness.batchprocessing;

import customerproject.customerbusiness.entities.User;
import customerproject.customerbusiness.facades.UserManagerLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Timer;

/**
 *
 * @author timovaananen
 */
@Singleton
@LocalBean
public class EmailRegistrationBatchProcessing implements Serializable {

   @EJB
   UserManagerLocal userManager;
   
   @SuppressWarnings("unused")
   @Schedule(minute = "*", hour = "*", dayOfWeek = "*", dayOfMonth = "*", month = "*", year = "*", info = "EmailRegistrationProcess")
   private void scheduledEmailRegistrationProcess(final Timer t) {
       
       System.out.println("scheduledEmailRegistrationProcess()");
       
       checkExpiredEmailRegistrations();
   }
   
   private void checkExpiredEmailRegistrations() {
       
       List<User> expiredUsers = userManager.getExpiredEmailRegistrations();
       
       System.out.println("Found: "+expiredUsers.size());
       
       for(int i = 0; i < expiredUsers.size(); i++)
           userManager.removeUser(expiredUsers.get(i));
   }
}
