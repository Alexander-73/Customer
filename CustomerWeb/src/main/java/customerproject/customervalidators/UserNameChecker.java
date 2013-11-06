/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customervalidators;

import customerproject.customerbusiness.facades.UserManagerLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author timovaananen
 */

@RequestScoped
@ManagedBean
public class UserNameChecker implements Validator, Serializable {

    @EJB
    UserManagerLocal userManager;
      
  
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String username = (String) value;
        System.out.println("Value: "+username);
        UIInput input = (UIInput) component;
        System.out.println("Comp sbm value: "+input.getSubmittedValue());
        System.out.println("Comp value: "+input.getValue());
        System.out.println("Local value: "+input.getLocalValue());
        
        if(userManager.getUserByUsername(username) != null) {
           
            input.setSubmittedValue("");
            
            context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is allready registered", null));
            
            
        }
        else if(username.equals("")) {
            context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is required", null));
        }
        
    }
    
    
    
}
