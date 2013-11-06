/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customerweb;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author timovaananen
 */
@RequestScoped
@ManagedBean(name="notificationBean")
public class NotificationBean {
    
    @ManagedProperty("#{param.state}")
    private String state;
    
    private String displayInfo;

    
    @PostConstruct
    public void init() {
        
        System.out.println("NotificationBean init()"+this.state);
        if(this.state != null) {
            
            if(this.state.equals("success"))
                this.displayInfo = "Registration complete. Please login";
            else if(state.equals("error"))
                this.displayInfo = "Registration failed. Unvalid/expired verification";
            
        }
                
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getDisplayInfo() {
        return displayInfo;
    }

    public void setDisplayInfo(String displayInfo) {
        this.displayInfo = displayInfo;
    }

    
}
