/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customervalidators;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author timovaananen
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String email = (String) value;
        System.out.println("Email: "+email);
        String confirm = (String) component.getAttributes().get("confirmemail");
        System.out.println("Confirm: "+confirm);
        if (email == null || confirm == null) {
            return; // Just ignore and let required="true" do its job.
        }

        if (!email.equals(confirm)) {
            System.out.println("Not equal");
            //throw new ValidatorException(new FacesMessage("messages", "Passwords are not equal."));
            context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Emails are not equal", null));
        }
    }
    
    
}
