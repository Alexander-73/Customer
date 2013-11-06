/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerbusiness.facades;

import customerproject.customerbusiness.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author timovaananen
 */
@Local
public interface UserManagerLocal {
    
   public User getCustomer(String user, String pass);
   
   public void registerCustomer(User cust);

    public void updateLoginData(User user);

    public void preRegisterUser(User user);

    public User getUserByUsernameEmail(String userName, String email);

    public List<User> getExpiredEmailRegistrations();

    public void removeUser(User user);

    public User getEmailVerification(String ver);

    public User getUserByUsername(String username);

}
