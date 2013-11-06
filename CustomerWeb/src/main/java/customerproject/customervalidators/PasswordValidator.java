/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customerproject.customervalidators;

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
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String password = (String) value;
        System.out.println("Password: "+password);
        String confirm = (String) component.getAttributes().get("confirm");
        System.out.println("Confirm: "+confirm);
        if (password == null || confirm == null) {
            return; // Just ignore and let required="true" do its job.
        }

        if (!password.equals(confirm)) {
            System.out.println("Not equal");
            //throw new ValidatorException(new FacesMessage("messages", "Passwords are not equal."));
            context.addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords are not equal", null));
        }
    }
    
}
