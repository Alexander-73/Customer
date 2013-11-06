/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerweb;

import customerproject.customerbusiness.entities.User;
import customerproject.customerbusiness.facades.CustomerManagerLocal;
import customerproject.customerbusiness.facades.UserManagerLocal;
import customerproject.customerintegration.facades.EmailLocalManager;
import customerproject.customerutilities.Utilities;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 *
 * @author timovaananen
 */
@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    
    private User user;
    private String password = "";
    private String password2 = "";
    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email = "";
    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email2 = "";
    private String userName;
    private String linkToDirect;
    private String feedback;
    private List<User> userList = new ArrayList<User>();

    
    
    @EJB
    UserManagerLocal userManager;
    
    @EJB
    EmailLocalManager emailManager;

    @PostConstruct
    public void init() {
        
        this.user = new User();
    }
    
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String checkCustomer() throws NoSuchAlgorithmException, UnsupportedEncodingException, UnknownHostException {
        
        try {
            System.out.println("Ip: "+InetAddress.getLocalHost().getHostAddress().trim());
        } catch (UnknownHostException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.user = userManager.getCustomer(this.user.getUsername(), Utilities.MD5(this.getPassword()));
        String retUrl = "";
        
        if(this.user == null)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed. Check your credentials", null));
            
            this.user = new User();
            this.setPassword("");
            this.feedback = "";
            
            retUrl = "/login"+"?faces-redirect=true";
        }
        else
        {
            System.out.println("Login success");
            this.feedback = "";
            
            this.user.setLoggedIn(true);
            this.user.setLastLogin(new Date());
            this.user.setIpAddress(InetAddress.getLocalHost().getHostAddress().trim());
            userManager.updateLoginData(this.user);
            
            this.userList.add(this.user);
            retUrl = "/index"+"?faces-redirect=true";
        }
        
        return retUrl;
    }
    
    public String directTo() {
        System.out.println("Redirecting...");
        //return "/register?faces-redirect=true";
        
        return this.linkToDirect;
    }
    
    public String logOut()
    {
        this.user.setLoggedIn(false);
        
        this.userManager.updateLoginData(user);
        
        init();
        return "/login"+"?faces-redirect=true";
    }
    
    
    /*
     * 
     * 
     * @author Timo Väänänen
     * 28.10.2013
     * method preRegister
     * In this method check for allready registered user with username+email
     * If user allready exists, will stay on the login page
     * If user doesn't exist, make a preregistration with username, password, email and
     * send confirmation email. Confirmation is valid for 2 hours, within that time User must 
     * make a confirmation. If done, other fields are updated accordingly
     */
    public void preRegister() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        System.out.println("preRegister()");
        
        this.user = this.userManager.getUserByUsernameEmail(this.userName, this.email);
        
        if(this.user == null)
        {
           this.user = new User();
           this.user.setUsername(this.userName);
           this.user.setPassword(Utilities.MD5(this.password));
           this.user.setEmail(this.email);
           this.user.setConfirmed(false);
           
           DateTime now = new DateTime(new Date());
           this.user.setValidUntil(now.plus(Period.minutes(120)).toDate());
           
           this.user.setVerificationHash(Utilities.SHA256(this.user.getEmail(), this.user.getPassword()));
           
           userManager.preRegisterUser(this.user);
           
           if(emailManager.send(this.email, this.user.getVerificationHash())) {
               
               FacesContext context = FacesContext.getCurrentInstance();
               context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Pre Registration successful. Email sent to address: "+this.email, null));
           } 
           
        }
        else
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pre Registration failed. User/Email allready exist", null));
            
            //this.user = new User();
        }
        this.setUserName("");
        this.setPassword("");
        this.setPassword2("");
        this.setEmail("");
        this.setEmail2("");
        
    }
   
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }
    
     public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getLinkToDirect() {
        return linkToDirect;
    }

    public void setLinkToDirect(String linkToDirect) {
        this.linkToDirect = linkToDirect;
    }
    
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    

    
    
    
}
