/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerbusiness.facadeimpl;

import customerproject.customerbusiness.entities.User;
import customerproject.customerbusiness.facades.UserManagerLocal;
import customerproject.customerbusiness.services.UserService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author timovaananen
 */
@Stateless
public class UserManager implements UserManagerLocal, Serializable {
    
    @EJB
    UserService userService;
    
    @Override
    public User getCustomer(String user, String pass) {
        return userService.getCustomer(user, pass);
    }

    @Override
    public void registerCustomer(User cust) {
        
        userService.registerCustomer(cust);
    }

    @Override
    public void updateLoginData(User user) {
        
        userService.updateLoginData(user);
    }

    @Override
    public void preRegisterUser(User user) {
        userService.preRegisterUser(user);
    }

    @Override
    public User getUserByUsernameEmail(String userName, String email) {
        
        return userService.getUserByUsernameEmail(userName, email);
    }

    @Override
    public List<User> getExpiredEmailRegistrations() {
        
        return userService.getExpiredEmailRegistrations();
    }

    @Override
    public void removeUser(User user) {
        
        userService.removeUser(user);
    }

    @Override
    public User getEmailVerification(String ver) {
        
        return userService.getEmailVerification(ver);
    }

    @Override
    public User getUserByUsername(String username) {
        
        return userService.getUserByUsername(username);
    }

}
