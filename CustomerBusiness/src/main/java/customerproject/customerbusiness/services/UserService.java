/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerbusiness.services;

import customerproject.customerbusiness.entities.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author timovaananen
 */
@Stateless
@LocalBean
public class UserService implements Serializable {
    
    @PersistenceContext(unitName="User_PU")
    EntityManager em;

    public User getCustomer(String user, String pass) {
        User ce;
        
        try
        {
            ce = (User) em.createNamedQuery("User.findByUsernamePassword", User.class).setParameter("password", pass).
                setParameter("username", user).getSingleResult();
        }
        catch(NoResultException nre) {
            ce = null;
        }
        return ce;
    }

    public void registerCustomer(User cust) {
        
       
        em.persist(cust);
    }

    public void updateLoginData(User user) {
        
        em.merge(user);
    }

    public void preRegisterUser(User user) {
        
        em.persist(user);
    }

    public User getUserByUsernameEmail(String userName, String email) {
        
        User ce;
        
        try
        {
            ce = (User) em.createNamedQuery("User.findByUsernameEmail", User.class).setParameter("username", userName).
                setParameter("email", email).getSingleResult();
        }
        catch(NoResultException nre) {
            ce = null;
        }
        return ce;
    }

    public List<User> getExpiredEmailRegistrations() {
        
        Date now = new Date();
        return em.createNamedQuery("User.findExpiredEmailRegistrations", User.class).setParameter("date", now).getResultList();
    }

    public void removeUser(User user) {
        
        User u = em.merge(user);
        em.remove(u);
    }

    public User getEmailVerification(String ver) {
        User ce;
        
        try
        {
            ce = (User) em.createNamedQuery("User.findEmailVerification", User.class).setParameter("hash", ver).
                getSingleResult();
        }
        catch(NoResultException nre) {
            ce = null;
        }
        return ce;
        
    }

    public User getUserByUsername(String username) {
        
         User ce;
        
        try
        {
            ce = (User) em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).
                getSingleResult();
        }
        catch(NoResultException nre) {
            ce = null;
        }
        return ce;
        
    }
}
